package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.request.TransferRequest;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequestMapping("/api/distributors")
public class DistributorController extends GenericController<Distributor> {

    private final DistributorService distributorService;

    @Autowired
    public DistributorController(DistributorService distributorService) {
        super(distributorService);
        this.distributorService = distributorService;
    }

    @PostMapping("/transfers")
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest request) {
        distributorService.transferProduct(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

