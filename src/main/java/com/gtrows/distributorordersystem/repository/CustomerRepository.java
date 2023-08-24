package com.gtrows.DistributorOrderSystem.repository;

import com.gtrows.DistributorOrderSystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
