package com.gtrows.distributorordersystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gtrows.distributorordersystem.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}

