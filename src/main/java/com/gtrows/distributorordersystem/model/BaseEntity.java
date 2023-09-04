package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;

public abstract class BaseEntity {
    @Id
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
