package com.gtrows.DistributorOrderSystem.model;

public class CreateOrderRequest {
    private Product product;
    private StoredProduct storedProduct;

    // Constructors
    public CreateOrderRequest() {
    }

    public CreateOrderRequest(Product product, StoredProduct storedProduct) {
        this.product = product;
        this.storedProduct = storedProduct;
    }

    // Getters and Setters

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public StoredProduct getStoredProduct() {
        return storedProduct;
    }

    public void setStoredProduct(StoredProduct storedProduct) {
        this.storedProduct = storedProduct;
    }
}
