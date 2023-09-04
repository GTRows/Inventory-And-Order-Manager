package com.gtrows.DistributorOrderSystem.model;


import org.springframework.data.annotation.Id;

public class Order extends BaseEntity{
    private String productId;
    private int quantity;

    // Constructors
    public Order() {
    }

    public Order(String productId, int quantity) {
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
        return "Order{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
