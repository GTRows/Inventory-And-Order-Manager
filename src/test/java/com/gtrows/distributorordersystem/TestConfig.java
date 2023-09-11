package com.gtrows.DistributorOrderSystem;

import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

@Configuration
@TestPropertySource(locations = "classpath:test-application.properties")
public class TestConfig {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void initialize() {
        resetDatabase();
        initTestData();
    }

    private void resetDatabase() {
        productRepository.deleteAll();
        warehouseRepository.deleteAll();
        distributorRepository.deleteAll();
        customerRepository.deleteAll();
    }

    private void initTestData() {
        initProducts(productRepository);
        initWarehouse(warehouseRepository);
        initDistributors(distributorRepository);
        initCustomers(customerRepository);
    }

    private void initProducts(ProductRepository productRepository) {
        productRepository.save(new Product("P1", 10.0));
        productRepository.save(new Product("P2", 20.0));
        productRepository.save(new Product("P3", 30.0));
    }

    private void initWarehouse(WarehouseRepository warehouseRepository) {
        warehouseRepository.save(Warehouse.getInstance());
    }

    private void initDistributors(DistributorRepository distributorRepository) {
        ArrayList<StoredProduct> storedProducts = new ArrayList<>();
        ArrayList<Product> products = (ArrayList<Product>) productRepository.findAll();
        storedProducts.add(new StoredProduct(products.get(0).getId(), 10));
        storedProducts.add(new StoredProduct(products.get(1).getId(), 20));
        storedProducts.add(new StoredProduct(products.get(2).getId(), 30));

        distributorRepository.save(new Distributor(Distributor.DistributorType.MAIN, null));
        distributorRepository.save(new Distributor(Distributor.DistributorType.SUB, storedProducts));
    }

    private void initCustomers(CustomerRepository customerRepository) {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("P1", 10));
        orders.add(new Order("P2", 20));

        customerRepository.save(new Customer("Customer 1", null));
        customerRepository.save(new Customer("2", null));
        customerRepository.save(new Customer("3", orders));
    }
}
