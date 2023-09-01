package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DistributorService extends GenericService<Distributor> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    public DistributorService(DistributorRepository repository) {
        super(repository);
    }

    public void transferProduct(TransferType sourceType, String sourceId, TransferType targetType, String targetId, String productId, int quantity) {
        // Product Control
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        Distributor sourceDistributor = null;
        Distributor targetDistributor = null;
        Warehouse sourceWarehouse = null;
        Warehouse targetWarehouse = null;

        // Source Control
        if (sourceType == TransferType.MAIN_DISTRIBUTOR || sourceType == TransferType.SUB_DISTRIBUTOR) {
            sourceDistributor = distributorRepository.findById(sourceId).orElseThrow(() -> new IllegalArgumentException("Source Distributor not found!"));
            checkProductAvailability(sourceDistributor.getProductsInStock(), productId, quantity);
            sourceDistributor.setProductsInStock(removeProductFromList(sourceDistributor.getProductsInStock(), productId, quantity));
        } else if (sourceType == TransferType.WAREHOUSE) {
            sourceWarehouse = warehouseRepository.findById(sourceId).orElseThrow(() -> new IllegalArgumentException("Source Warehouse not found!"));
            checkProductAvailability(sourceWarehouse.getStoredProducts(), productId, quantity);
            sourceWarehouse.setStoredProducts(removeProductFromList(sourceWarehouse.getStoredProducts(), productId, quantity));
        } else {
            throw new IllegalArgumentException("Source Type not found!");
        }


        // Target Control
        if (targetType == TransferType.MAIN_DISTRIBUTOR || targetType == TransferType.SUB_DISTRIBUTOR) {
            targetDistributor = distributorRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("Target Distributor not found!"));
            targetDistributor.setProductsInStock(updateProductList(targetDistributor.getProductsInStock(), productId, quantity));
        } else if (targetType == TransferType.WAREHOUSE) {
            targetWarehouse = warehouseRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("Target Warehouse not found!"));
            targetWarehouse.setStoredProducts(updateProductList(targetWarehouse.getStoredProducts(), productId, quantity));
        } else {
            throw new IllegalArgumentException("Target Type not found!");
        }

        // Save both
        if (sourceDistributor != null) {
            System.out.println(1);
            distributorRepository.save(sourceDistributor);
        } else {
            System.out.println(2);
            warehouseRepository.save(sourceWarehouse);
        }

        if (targetDistributor != null) {
            System.out.println(3);
            System.out.println(targetDistributor.toString());
            distributorRepository.save(targetDistributor);
        } else {
            System.out.println(4);
            warehouseRepository.save(targetWarehouse);
        }
    }


    private List<StoredProduct> updateProductList(List<StoredProduct> products, String productId, int quantity) {
        if (products == null) {
            products = new ArrayList<>();
        }
        Optional<StoredProduct> existingProductOpt = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
        if (existingProductOpt.isPresent()) {
            StoredProduct existingProduct = existingProductOpt.get();
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {
            products.add(new StoredProduct(productId, quantity));
        }
        return products;
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

        if (!productOpt.isPresent()) {
            throw new IllegalArgumentException("The specified product is not in stock!");
        }

        if (productOpt.get().getQuantity() < quantity) {
            throw new IllegalArgumentException("There is not enough of the specified product!");
        }
    }


}
