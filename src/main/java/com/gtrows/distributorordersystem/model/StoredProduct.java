package com.gtrows.DistributorOrderSystem.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoredProduct extends BaseEntity {
    private String productId;
    private int quantity;
}
