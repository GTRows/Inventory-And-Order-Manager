package com.gtrows.distributorordersystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "warehouses")
public class Warehouse {
    @Id
    private String id;
    private String address;
    private List<StoredProduct> storedProducts;  // Assuming you have a StoredProduct class to represent products in the warehouse with their quantities

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

// Assuming you have a StoredProduct class to represent products in the warehouse with their quantities.
// This class associates a product with its quantity in the warehouse.
class StoredProduct {
    private String productId;  // ID reference to the Product
    private int quantity;

    // Constructors
    public StoredProduct() {
    }

    public StoredProduct(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StoredProduct{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}