package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.Util.ProductUtils;
import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import com.gtrows.DistributorOrderSystem.request.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DistributorService extends GenericService<Distributor> {

    private final ProductRepository productRepository;

    private final DistributorRepository distributorRepository;

    private final WarehouseRepository warehouseRepository;

    private final WarehouseService warehouseService;

    @Autowired
    public DistributorService(DistributorRepository repository, ProductRepository productRepository, WarehouseRepository warehouseRepository, WarehouseService warehouseService) {
        super(repository);
        this.productRepository = productRepository;
        this.distributorRepository = repository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseService = warehouseService;
    }

    @Override
    public Distributor save(Distributor distributor) {
        if ("0".equals(distributor.getId())) {
            if (this.getById("0").isEmpty()) {
                distributor.setId("0");
            } else {
                throw new IllegalArgumentException("Main Distributor already exists!");
            }
        }
        return super.save(distributor);
    }

    @Override
    public void delete(String id) {
        Distributor distributor = getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Distributor not found!"));

        if (!Objects.equals(distributor.getId(), "0")) {
            transferStockToMainDistributor(id);
        }

        if ("0".equals(id)) {
            throw new IllegalArgumentException("Main Distributor can't be deleted!");
        }
        super.delete(id);
    }


    public void transferStockToMainDistributor(String distributorId) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new IllegalArgumentException("Distributor not found!"));

        Distributor mainDistributor = distributorRepository.findById("0")
                .orElseThrow(() -> new IllegalArgumentException("Main Distributor not found!"));

        List<StoredProduct> distributorStock = distributor.getProductsInStock();
        List<StoredProduct> mainDistributorStock = mainDistributor.getProductsInStock();


        for (StoredProduct storedProduct : distributorStock) {
            // Check if main distributor already has this product in stock
            Optional<StoredProduct> existingProductOpt = mainDistributorStock.stream()
                    .filter(p -> p.getProductId().equals(storedProduct.getProductId()))
                    .findFirst();

            if (existingProductOpt.isPresent()) {
                StoredProduct existingProduct = existingProductOpt.get();
                existingProduct.setQuantity(existingProduct.getQuantity() + storedProduct.getQuantity());
            } else {
                mainDistributorStock.add(storedProduct);
            }
        }

        // Clear the stock of the distributor
        distributor.setProductsInStock(null);

        // distributorRepository.save(distributor);
        distributorRepository.save(mainDistributor);
    }


    public void transferProduct(TransferRequest transferRequest) {
        productRepository.findById(transferRequest.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        Distributor sourceDistributor = null;
        Distributor targetDistributor = null;
        Warehouse sourceWarehouse = null;
        Warehouse targetWarehouse = null;

        // Check forbidden transfers
        if (transferRequest.getSourceType() == TransferType.SUB_DISTRIBUTOR && (transferRequest.getTargetType() == TransferType.MAIN_DISTRIBUTOR || transferRequest.getTargetType() == TransferType.WAREHOUSE)) {
            throw new IllegalArgumentException("Sub Distributor can't transfer to Main Distributor or Warehouse!");
        }
        if (transferRequest.getSourceType() == TransferType.MAIN_DISTRIBUTOR && transferRequest.getTargetType() == TransferType.WAREHOUSE) {
            throw new IllegalArgumentException("Main Distributor can't transfer to Warehouse!");
        }
        if (transferRequest.getSourceType() == TransferType.WAREHOUSE && transferRequest.getTargetType() == TransferType.SUB_DISTRIBUTOR) {
            throw new IllegalArgumentException("Warehouse can't transfer to Sub Distributor!");
        }

        // Source Control
        if (transferRequest.getSourceType() == TransferType.MAIN_DISTRIBUTOR || transferRequest.getSourceType() == TransferType.SUB_DISTRIBUTOR) {
            sourceDistributor = distributorRepository.findById(transferRequest.getSourceId()).orElseThrow(() -> new IllegalArgumentException("Source Distributor not found!"));
            checkProductAvailability(sourceDistributor.getProductsInStock(), transferRequest.getProductId(), transferRequest.getQuantity());
            sourceDistributor.setProductsInStock(removeProductFromList(sourceDistributor.getProductsInStock(), transferRequest.getProductId(), transferRequest.getQuantity()));
        } else if (transferRequest.getSourceType() == TransferType.WAREHOUSE) {
            sourceWarehouse = Warehouse.getInstance();
            checkProductAvailability(sourceWarehouse.getStoredProducts(), transferRequest.getProductId(), transferRequest.getQuantity());
            sourceWarehouse.setStoredProducts(removeProductFromList(sourceWarehouse.getStoredProducts(), transferRequest.getProductId(), transferRequest.getQuantity()));
        } else {
            throw new IllegalArgumentException("Source Type not found!");
        }

        // Target Control
        if (transferRequest.getTargetType() == TransferType.MAIN_DISTRIBUTOR || transferRequest.getTargetType() == TransferType.SUB_DISTRIBUTOR) {
            targetDistributor = distributorRepository.findById(transferRequest.getTargetId()).orElseThrow(() -> new IllegalArgumentException("Target Distributor not found!"));
            targetDistributor.setProductsInStock(ProductUtils.updateProductList(targetDistributor.getProductsInStock(), transferRequest.getProductId(), transferRequest.getQuantity()));
        } else if (transferRequest.getTargetType() == TransferType.WAREHOUSE) {
            targetWarehouse = Warehouse.getInstance();
            targetWarehouse.setStoredProducts(ProductUtils.updateProductList(targetWarehouse.getStoredProducts(), transferRequest.getProductId(), transferRequest.getQuantity()));
        } else {
            throw new IllegalArgumentException("Target Type not found!");
        }
        // Save both
        if (sourceDistributor != null) {
            super.save(sourceDistributor);
        } else {
            warehouseService.save(sourceWarehouse);
        }

        if (targetDistributor != null) {
            super.save(targetDistributor);
        } else {
            warehouseService.save(targetWarehouse);
        }
    }

    private List<StoredProduct> removeProductFromList(List<StoredProduct> products, String productId, int quantity) {
        Optional<StoredProduct> existingProduct = products.stream().filter(p -> p.getProductId().equals(productId)).findFirst();
        if (existingProduct.isPresent()) {
            int updatedQuantity = existingProduct.get().getQuantity() - quantity;
            if (updatedQuantity <= 0) {
                products.remove(existingProduct.get());
            } else {
                existingProduct.get().setQuantity(updatedQuantity);
            }
        }
        return products;
    }

    private void checkProductAvailability(List<StoredProduct> products, String productId, int quantity) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("There are no products in stock!");
        }

        Optional<StoredProduct> productOpt = products.stream().filter(p -> p.getProductId().equals(productId)).findFirst();

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("The specified product is not in stock!");
        }

        if (productOpt.get().getQuantity() < quantity) {
            throw new IllegalArgumentException("There is not enough of the specified product!");
        }
    }
}


