package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "warehouses")
@Getter
@Setter
public class Warehouse {
    private static Warehouse instance;
    private List<StoredProduct> storedProducts = new ArrayList<>();

    private Warehouse() {
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }
}
