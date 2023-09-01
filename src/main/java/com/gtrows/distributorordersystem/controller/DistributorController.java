package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import com.gtrows.DistributorOrderSystem.request.ProductTransferRequest;
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

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestParam TransferType sourceType, @RequestParam String sourceId, @RequestParam TransferType targetType, @RequestParam String targetId, @RequestBody ProductTransferRequest request) {
        System.out.println("Transfering product from " + sourceType + " " + sourceId + " to " + targetType + " " + targetId);
        try {
            distributorService.transferProduct(sourceType, sourceId, targetType, targetId, request.getProductId(), request.getQuantity());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

