package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService extends GenericService<Warehouse> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public WarehouseService(WarehouseRepository repository) {
        super(repository);
    }

    public Product addProductToWarehouse(String productId, String warehouseId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        Warehouse warehouse = getById(warehouseId).orElseThrow(() -> new IllegalArgumentException("Warehouse not found!"));

        StoredProduct storedProduct = new StoredProduct(product.getId(), quantity);
        warehouse.getStoredProducts().add(storedProduct);

        save(warehouse);

        return product;
    }

    public void removeProductFromWarehouse(String productId, String warehouseId) {
        Warehouse warehouse = getById(warehouseId).orElseThrow(() -> new IllegalArgumentException("Warehouse not found!"));

        StoredProduct productToRemove = warehouse.getStoredProducts().stream()
                .filter(storedProduct -> storedProduct.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in warehouse!"));

        warehouse.getStoredProducts().remove(productToRemove);

        save(warehouse);
    }

    public void reduceStockInWarehouse(String productId, String warehouseId, int quantityToReduce) {
        Warehouse warehouse = getById(warehouseId).orElseThrow(() -> new IllegalArgumentException("Warehouse not found!"));

        StoredProduct storedProduct = warehouse.getStoredProducts().stream()
                .filter(sp -> sp.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in warehouse!"));

        int newQuantity = storedProduct.getQuantity() - quantityToReduce;

        if (newQuantity <= 0) {
            warehouse.getStoredProducts().remove(storedProduct);
        } else {
            storedProduct.setQuantity(newQuantity);
        }

        save(warehouse);
    }

    public void increaseStockInWarehouse(String productId, String warehouseId, int quantityToAdd) {
        Warehouse warehouse = getById(warehouseId).orElseThrow(() -> new IllegalArgumentException("Warehouse not found!"));

        StoredProduct storedProduct = warehouse.getStoredProducts().stream()
                .filter(sp -> sp.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in warehouse!"));

        int newQuantity = storedProduct.getQuantity() + quantityToAdd;
        storedProduct.setQuantity(newQuantity);

        save(warehouse);
    }


}