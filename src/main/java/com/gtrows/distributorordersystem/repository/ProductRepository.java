package com.gtrows.DistributorOrderSystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gtrows.DistributorOrderSystem.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
