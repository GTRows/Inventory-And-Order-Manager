package com.gtrows.DistributorOrderSystem.repository;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DistributorRepository extends MongoRepository<Distributor, String> {
}
