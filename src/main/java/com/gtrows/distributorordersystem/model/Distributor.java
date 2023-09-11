package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "distributors")
@Getter
@Setter
@NoArgsConstructor
public class Distributor extends BaseEntity {

    public enum DistributorType {
        MAIN,
        SUB
    }

    private String distributorId;
    private List<StoredProduct> productsInStock = new ArrayList<>();

    public Distributor(DistributorType distributorType, List<StoredProduct> productsInStock) {
        if (distributorType == DistributorType.MAIN) {
            super.setId("0");
        } else {
            // random id
            super.setId(UUID.randomUUID().toString());
        }
        this.productsInStock = (productsInStock == null) ? new ArrayList<>() : productsInStock;
    }
}
