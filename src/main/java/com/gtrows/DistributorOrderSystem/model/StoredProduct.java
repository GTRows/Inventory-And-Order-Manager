package com.gtrows.DistributorOrderSystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoredProduct extends BaseEntity implements Cloneable {
    private String productId;
    private int quantity;

    @Override
    public StoredProduct clone() {
        try {
            return (StoredProduct) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("The StoredProduct class does not support cloning", e);
        }
    }
}
