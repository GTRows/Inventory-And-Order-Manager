package com.gtrows.DistributorOrderSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gtrows.DistributorOrderSystem.model.Product;
import org.springframework.data.mongodb.repository.Query;
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'productName': ?0}")
    Product findByName(String productName);
}
