package com.gtrows.DistributorOrderSystem.repository;

import com.gtrows.DistributorOrderSystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query("{'name': ?0}")
    Customer findByName(String name);
}
