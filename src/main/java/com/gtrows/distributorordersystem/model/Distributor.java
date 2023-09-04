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

    private String connectedMainDistributorId;
    private List<StoredProduct> productsInStock;

    // Constructors
    public Distributor() {
    }


    public Distributor(String id, DistributorType distributorType, String connectedMainDistributorId, String address, List<StoredProduct> productsInStock, boolean isActive) {
        this.id = id;
        this.distributorType = distributorType;
        this.connectedMainDistributorId = connectedMainDistributorId;
        this.productsInStock = productsInStock;
    }

    // Getters and Setters

    public String getConnectedMainDistributorId() {
        return connectedMainDistributorId;
    }

    public void setConnectedMainDistributorId(String connectedMainDistributorId) {
        this.connectedMainDistributorId = connectedMainDistributorId;
    }

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


    public List<StoredProduct> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<StoredProduct> productsInStock) {
        this.productsInStock = productsInStock;
    }


    @Override
    public String toString() {
        return "Distributor{" +
                "id='" + id + '\'' +
                ", distributorType=" + distributorType +
                ", productsInStock=" + productsInStock +
                '}';
    }
}