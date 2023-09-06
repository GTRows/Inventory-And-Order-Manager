package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "warehouses")
public class Warehouse extends BaseEntity {
    private List<StoredProduct> storedProducts;

    // Constructors
    public Warehouse() {
        this.storedProducts = new ArrayList<>();
    }

    public Warehouse(List<StoredProduct> storedProducts) {
        this.storedProducts = storedProducts;
    }

    // Getters and Setters
    public List<StoredProduct> getStoredProducts() {
        return storedProducts;
    }

    public void setStoredProducts(List<StoredProduct> storedProducts) {
        this.storedProducts = (storedProducts == null) ? new ArrayList<>() : storedProducts;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id='" + id + '\'' +
                ", storedProducts=" + storedProducts +
                '}';
    }
}