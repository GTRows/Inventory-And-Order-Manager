package com.gtrows.DistributorOrderSystem.request;

import com.gtrows.DistributorOrderSystem.model.Order;

public class OrderRequest {
    private String customerId;
    private Order order;
    private String distributorId;

    // Constructors
    public OrderRequest() {
    }

    public OrderRequest(String customerId, Order order, String distributorId) {
        this.customerId = customerId;
        this.order = order;
        this.distributorId = distributorId;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "customerId='" + customerId + '\'' +
                ", order=" + order +
                ", distributorId='" + distributorId + '\'' +
                '}';
    }
}
