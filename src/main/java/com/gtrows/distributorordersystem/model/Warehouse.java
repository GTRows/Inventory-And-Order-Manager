package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String id;
    private String address;
    private List<StoredProduct> storedProducts;

    // Constructors
    public Warehouse() {
    }

    public Warehouse(String address, List<StoredProduct> storedProducts) {
        this.address = address;
        this.storedProducts = storedProducts;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<StoredProduct> getStoredProducts() {
        return storedProducts;
    }

    public void setStoredProducts(List<StoredProduct> storedProducts) {
        this.storedProducts = storedProducts;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", storedProducts=" + storedProducts +
                '}';
    }
}