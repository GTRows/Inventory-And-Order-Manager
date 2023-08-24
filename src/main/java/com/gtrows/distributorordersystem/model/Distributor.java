package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "distributors")
public class Distributor {

    public enum DistributorType {
        MAIN,
        SUB
    }

    @Id
    private String id;

    private DistributorType distributorType;
    private String address;
    private List<StoredProduct> productsInStock;  // Using the StoredProduct class to represent products with their quantities in distributor's stock
    private boolean isActive;

    // Constructors
    public Distributor() {
    }

    public Distributor(DistributorType distributorType, String address, List<StoredProduct> productsInStock, boolean isActive) {
        this.distributorType = distributorType;
        this.address = address;
        this.productsInStock = productsInStock;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DistributorType getDistributorType() {
        return distributorType;
    }

    public void setDistributorType(DistributorType distributorType) {
        this.distributorType = distributorType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<StoredProduct> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<StoredProduct> productsInStock) {
        this.productsInStock = productsInStock;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "id='" + id + '\'' +
                ", distributorType=" + distributorType +
                ", address='" + address + '\'' +
                ", productsInStock=" + productsInStock +
                ", isActive=" + isActive +
                '}';
    }
}
