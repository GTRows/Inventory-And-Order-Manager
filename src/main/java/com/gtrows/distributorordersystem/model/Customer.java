package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

public class Customer {

    @Id
    private String id;

    private String Name;
//    private String lastName;
    private List<Order> orders;  // Assuming you have an Order class to represent individual orders

    // Constructors
    public Customer() {
    }

    public Customer(String id, String name, List<Order> orders) {
        this.id = id;
        Name = name;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}


