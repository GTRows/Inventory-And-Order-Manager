package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.CreateOrderRequest;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.service.WarehouseService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {


    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProductToWarehouse(@RequestBody StoredProduct storedProduct) {
        try {
            Product savedProduct = warehouseService.addProductToWarehouse(storedProduct.getProductId(), storedProduct.getQuantity());
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-products")
    public ResponseEntity<CreateOrderRequest> addProductToWarehouse(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            warehouseService.createAndProductToWarehouse(createOrderRequest);
            return new ResponseEntity<>(createOrderRequest, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProductFromWarehouse(@PathVariable String productId) {
        try {
            warehouseService.removeProductFromWarehouse(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Void> updateStockInWarehouse(@PathVariable String productId, @RequestBody int newQuantity) {
        try {
            warehouseService.updateStockInWarehouse(productId, newQuantity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
