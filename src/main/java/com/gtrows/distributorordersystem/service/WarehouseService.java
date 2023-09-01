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

    public void updateStockInWarehouse(String warehouseId, String productId, int newQuantity) {
        Warehouse warehouse = getById(warehouseId).orElse(null);

        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse not found!");
        }

        StoredProduct storedProduct = warehouse.getStoredProducts().stream()
                .filter(sp -> sp.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (storedProduct == null) {
            throw new IllegalArgumentException("Product not found in warehouse!");
        }

        storedProduct.setQuantity(newQuantity);
        save(warehouse);
    }


}