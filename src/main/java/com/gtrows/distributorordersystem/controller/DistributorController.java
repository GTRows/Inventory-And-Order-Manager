package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import com.gtrows.DistributorOrderSystem.request.ProductTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/distributors")
public class DistributorController extends GenericController<Distributor> {

    private final DistributorService distributorService;

    @Autowired
    public DistributorController(DistributorService distributorService) {
        super(distributorService);
        this.distributorService = distributorService;
    }

    @Override
    @PostMapping
    public Distributor create(@RequestBody Distributor distributor) {
        try {
            return distributorService.save(distributor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Distributor not found!");
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Distributor distributor = distributorService.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Distributor not found!"));
        if (!Objects.equals(distributor.getId(), "0")) {
            distributorService.transferStockToMainDistributor(id);
        }
        try {
            distributorService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/transfers")
    public ResponseEntity<Void> transfer(@RequestParam TransferType sourceType, @RequestParam String sourceId, @RequestParam TransferType targetType, @RequestParam String targetId, @RequestBody ProductTransferRequest request) {
        try {
            distributorService.transferProduct(sourceType, sourceId, targetType, targetId, request.getProductId(), request.getQuantity());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

