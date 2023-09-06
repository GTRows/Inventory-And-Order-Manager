package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "distributors")
public class Distributor extends BaseEntity {

    public enum DistributorType {
        MAIN,
        SUB
    }

    private String distributorId;

    private List<StoredProduct> productsInStock;

    // Constructors
    public Distributor() {
        this.productsInStock = new ArrayList<>();
    }

    public Distributor(DistributorType distributorType, List<StoredProduct> productsInStock) {
        if (distributorType == DistributorType.MAIN) {
            super.id = "0";
        } else {
            // random id
            super.id = UUID.randomUUID().toString();
        }
        this.productsInStock = (productsInStock == null) ? new ArrayList<>() : productsInStock;
    }

    // Getters and Setters
    public List<StoredProduct> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<StoredProduct> productsInStock) {
        this.productsInStock = (productsInStock == null) ? new ArrayList<>() : productsInStock;
    }


    @Override
    public String toString() {
        String type = (distributorId.equals("0")) ? "Main" : "Sub";
        String idField = (type.equals("Main")) ? "Main" : "id";

        return "Distributor{" +
                "Type = '" + type + "', " +
                idField + "='" + id + '\'' +
                ", productsInStock=" + productsInStock +
                '}';
    }

}