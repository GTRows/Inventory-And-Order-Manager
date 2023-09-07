package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.CreateOrderRequest;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WarehouseService {

    private final ProductRepository productRepository;

    private final WarehouseRepository warehouseRepository;


    @Autowired
    public WarehouseService(WarehouseRepository repository, ProductRepository productRepository) {
        this.warehouseRepository = repository;
        this.productRepository = productRepository;
    }

    public Product addProductToWarehouse(String productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        Warehouse warehouse = Warehouse.getInstance();

        StoredProduct storedProduct = new StoredProduct(product.getId(), quantity);
        warehouse.getStoredProducts().add(storedProduct);

        this.save(warehouse);

        return product;
    }

    public CreateOrderRequest createAndProductToWarehouse(CreateOrderRequest createOrderRequest) {
        // Control if the product is already in the warehouse
        if (!Warehouse.getInstance().getStoredProducts().stream().anyMatch(storedProduct -> storedProduct.getProductId().equals(createOrderRequest.getProduct().getId()))) {
            createOrderRequest.getStoredProduct().setProductId(createOrderRequest.getProduct().getId());
        }
        // Add the product to the warehouse
        addProductToWarehouse(createOrderRequest.getProduct().getId(), createOrderRequest.getStoredProduct().getQuantity());
        return createOrderRequest;
    }

    public void removeProductFromWarehouse(String productId) {
        Warehouse warehouse = Warehouse.getInstance();

        StoredProduct productToRemove = warehouse.getStoredProducts().stream()
                .filter(storedProduct -> storedProduct.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in warehouse!"));

        warehouse.getStoredProducts().remove(productToRemove);

        this.save(warehouse);
    }

    public void updateStockInWarehouse(String productId, int newQuantity) {
        Warehouse warehouse = Warehouse.getInstance();

        StoredProduct storedProduct = warehouse.getStoredProducts().stream()
                .filter(sp -> sp.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (storedProduct == null) {
            throw new IllegalArgumentException("Product not found in warehouse!");
        }

        storedProduct.setQuantity(newQuantity);
        this.save(warehouse);
    }

    @Transactional
    public void save(Warehouse entity) {
        warehouseRepository.save(entity);
    }
}