package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController extends GenericController<Warehouse> {

    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        super(warehouseService);
        this.warehouseService = warehouseService;
    }

    @PostMapping("/{warehouseId}/products")
    public ResponseEntity<Product> addProductToWarehouse(@PathVariable String warehouseId, @RequestBody StoredProduct storedProduct) {
        try {
            Product savedProduct = warehouseService.addProductToWarehouse(storedProduct.getProductId(), warehouseId, storedProduct.getQuantity());
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{warehouseId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWarehouse(@PathVariable String warehouseId, @PathVariable String productId) {
        try {
            warehouseService.removeProductFromWarehouse(productId, warehouseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{warehouseId}/products/{productId}/reduce")
    public ResponseEntity<Void> reduceStockInWarehouse(@PathVariable String warehouseId, @PathVariable String productId, @RequestBody int quantityToReduce) {
        try {
            warehouseService.reduceStockInWarehouse(productId, warehouseId, quantityToReduce);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{warehouseId}/products/{productId}/increase")
    public ResponseEntity<Void> increaseStockInWarehouse(@PathVariable String warehouseId, @PathVariable String productId, @RequestBody int quantityToAdd) {
        try {
            warehouseService.increaseStockInWarehouse(productId, warehouseId, quantityToAdd);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
