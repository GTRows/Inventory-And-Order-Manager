package com.gtrows.DistributorOrderSystem;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
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

    @Bean
    public CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new Product("P1", "Product 1", 10.0, 10));
                productRepository.save(new Product("P2", "Product 2", 20.0, 20));
                productRepository.save(new Product("P3", "Product 3", 30.0, 30));
                productRepository.save(new Product("P4", "Product 4", 40.0, 40));
                productRepository.save(new Product("P5", "Product 5", 50.0, 50));
                productRepository.save(new Product("P6", "Product 6", 60.0, 60));
                productRepository.save(new Product("P7", "Product 7", 70.0, 70));
                productRepository.save(new Product("P8", "Product 8", 80.0, 80));
                productRepository.save(new Product("P9", "Product 9", 90.0, 90));
                productRepository.save(new Product("P10", "Product 10", 100.0, 100));
            }
        };
    }

    @Bean
    public CommandLineRunner initWarehouse(WarehouseRepository warehouseRepository) {
        return args -> {
            if (warehouseRepository.count() == 0) {
                warehouseRepository.save(Warehouse.getInstance());
            }
        };
    }

    @Bean
    public CommandLineRunner initDistributor(DistributorRepository distributorRepository) {
        ArrayList<StoredProduct> storedProducts = new ArrayList<>();
        storedProducts.add(new StoredProduct("P1", 10));
        storedProducts.add(new StoredProduct("P2", 20));
        storedProducts.add(new StoredProduct("P3", 30));

        return args -> {
            if (distributorRepository.count() == 0) {
                distributorRepository.save(new Distributor(Distributor.DistributorType.MAIN, null));
                distributorRepository.save(new Distributor(Distributor.DistributorType.SUB, storedProducts));
            }
        };
    }

}
