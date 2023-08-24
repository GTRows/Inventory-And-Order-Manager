package com.gtrows.DistributorOrderSystem.repository;

import com.gtrows.DistributorOrderSystem.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
}
