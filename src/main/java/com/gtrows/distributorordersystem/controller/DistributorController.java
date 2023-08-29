package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distributors")
public class DistributorController extends GenericController<Distributor> {

    private final DistributorService distributorService;

    @Autowired
    public DistributorController(DistributorService distributorService) {
        super(distributorService);
        this.distributorService = distributorService;
    }

    @PostMapping("/{distributorId}/transfer-from-warehouse")
    public ResponseEntity<Product> transferProductFromWarehouse(@PathVariable String distributorId, @RequestBody ProductTransferRequest request){
        try {
            Product saveProduct = distributorService.transferProductFromWarehouseToMainDistributor(request.getProductId(), distributorId, request.getQuantity());
            return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static class ProductTransferRequest {
        private String productId;
        private int quantity;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
