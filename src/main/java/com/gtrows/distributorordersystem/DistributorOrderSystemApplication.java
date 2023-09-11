package com.gtrows.DistributorOrderSystem;

import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class DistributorOrderSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributorOrderSystemApplication.class, args);
    }

    private boolean isDatabaseEmpty(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                                    DistributorRepository distributorRepository, CustomerRepository customerRepository) {
        return productRepository.count() == 0 &&
                warehouseRepository.count() == 0 &&
                distributorRepository.count() == 0 &&
                customerRepository.count() == 0;
    }

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                                      DistributorRepository distributorRepository, CustomerRepository customerRepository) {
        return args -> {
            if (isDatabaseEmpty(productRepository, warehouseRepository, distributorRepository, customerRepository)) {
                initProducts(productRepository);
                initWarehouse(warehouseRepository);
                initDistributors(distributorRepository, productRepository);
                initCustomers(customerRepository);
            }
        };
    }

    private void initProducts(ProductRepository productRepository) {
        productRepository.save(new Product("P1", 10.0));
        productRepository.save(new Product("P2", 20.0));
        productRepository.save(new Product("P3", 30.0));
    }

    private void initWarehouse(WarehouseRepository warehouseRepository) {
        warehouseRepository.save(Warehouse.getInstance());
    }

    private void initDistributors(DistributorRepository distributorRepository, ProductRepository productRepository) {
        ArrayList<StoredProduct> storedProducts = new ArrayList<>();
        ArrayList<Product> products = (ArrayList<Product>) productRepository.findAll();
        System.out.println("Yardiiiiiim" + products.get(0).getId());
        storedProducts.add(new StoredProduct(products.get(0).getId(), 10));
        storedProducts.add(new StoredProduct(products.get(1).getId(), 20));
        storedProducts.add(new StoredProduct(products.get(2).getId(), 30));

        distributorRepository.save(new Distributor(Distributor.DistributorType.MAIN, null));
        distributorRepository.save(new Distributor(Distributor.DistributorType.SUB, null));
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
