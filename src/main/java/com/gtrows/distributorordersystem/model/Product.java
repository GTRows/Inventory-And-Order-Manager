package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product extends BaseEntity{
    private String productName;
    private Double price;


    // Constructors
    public Product() {
    }

    public Product(String productName, String description, Double price, Integer stockQuantity) {
        this.productName = productName;
        this.price = price;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
