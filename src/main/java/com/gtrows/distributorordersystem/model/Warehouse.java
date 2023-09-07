package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "warehouses")
public class Warehouse {
    private static Warehouse instance;
    private List<StoredProduct> storedProducts;


    // Constructors
    private Warehouse() {
        this.storedProducts = new ArrayList<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }


    // Getters and Setters
    public List<StoredProduct> getStoredProducts() {
        return storedProducts;
    }

    public void setStoredProducts(List<StoredProduct> storedProducts) {
        this.storedProducts = (storedProducts == null) ? new ArrayList<>() : storedProducts;
    }
}