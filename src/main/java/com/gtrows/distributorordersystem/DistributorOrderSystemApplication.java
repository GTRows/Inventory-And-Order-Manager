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
                productRepository.save(new Product("P1", 10.0));
                productRepository.save(new Product("P2", 20.0));
                productRepository.save(new Product("P3", 30.0));
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
