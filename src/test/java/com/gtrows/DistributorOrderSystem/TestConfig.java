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
        Product product1 = new Product("P1", 10.0);
        product1.setId("1");
        productRepository.save(product1);

        Product product2 = new Product("P2", 20.0);
        product2.setId("2");
        productRepository.save(product2);

        Product product3 = new Product("P3", 30.0);
        product3.setId("3");
        productRepository.save(product3);
    }

    public void initWarehouse(WarehouseRepository warehouseRepository) {
        warehouseRepository.save(Warehouse.getInstance());
    }

    public void initDistributors(DistributorRepository distributorRepository) {
        ArrayList<StoredProduct> storedProducts = new ArrayList<>();
        ArrayList<Product> products = (ArrayList<Product>) productRepository.findAll();
        storedProducts.add(new StoredProduct(products.get(0).getId(), 10));
        storedProducts.add(new StoredProduct(products.get(1).getId(), 20));
        storedProducts.add(new StoredProduct(products.get(2).getId(), 30));

        Warehouse.getInstance().setStoredProducts(storedProducts);
        warehouseRepository.save(Warehouse.getInstance());

        distributorRepository.save(new Distributor(Distributor.DistributorType.MAIN, storedProducts));

        Distributor subDistributor = new Distributor(Distributor.DistributorType.SUB, storedProducts);
        subDistributor.setId("1");
        distributorRepository.save(subDistributor);

        Distributor subDistributor2 = new Distributor(Distributor.DistributorType.SUB, null);
        subDistributor2.setId("2");
        distributorRepository.save(subDistributor2);
    }

    private void initCustomers(CustomerRepository customerRepository) {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("P1", 10));
        orders.add(new Order("P2", 20));

        Customer customer1 = new Customer("Customer 1", null);
        customer1.setId("1");
        customerRepository.save(customer1);

        Customer customer2 = new Customer("2", null);
        customer2.setId("2");
        customerRepository.save(customer2);

        Customer customer3 = new Customer("3", orders);
        customer3.setId("3");
        customerRepository.save(customer3);
    }
}
