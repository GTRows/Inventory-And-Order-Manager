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

    public Product addProductToWarehouse(Product product, String warehouseId) {
        Product savedProduct = productRepository.save(product);

        Warehouse warehouse = getById(warehouseId).orElseThrow(() -> new IllegalArgumentException("Depo bulunamadı!"));

        StoredProduct storedProduct = new StoredProduct(savedProduct.getId(), savedProduct.getStockQuantity());
        warehouse.getStoredProducts().add(storedProduct);

        save(warehouse);

        return savedProduct;
    }
}