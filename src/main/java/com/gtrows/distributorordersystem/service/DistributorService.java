package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributorService extends GenericService<Distributor> {
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public DistributorService(DistributorRepository repository) {
        super(repository);
    }

    public Product transferProductFromWarehouseToMainDistributor(String productId, String distributorId, int quantity){
        // Distributor Control
        Distributor distributor = getById(distributorId).orElseThrow(() -> new IllegalArgumentException("Distributor not found!"));

        // Ensure it's a main distributor
        if (distributor.getDistributorType() != Distributor.DistributorType.MAIN) {
            throw new IllegalArgumentException("Only main distributors can take products!");
        }

        // Retrieve product
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        // Stock control
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock!");
        }

        // Transfering
        distributor.getProductsInStock().add(new StoredProduct(productId,quantity));
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        save(distributor);

        return product;
    }
}