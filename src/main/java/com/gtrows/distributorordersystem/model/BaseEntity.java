package com.gtrows.DistributorOrderSystem.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    @Setter
    private String id;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
