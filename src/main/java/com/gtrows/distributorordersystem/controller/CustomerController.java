package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Customer;
import com.gtrows.DistributorOrderSystem.service.CustomerService;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Api", description = "For Customer Operations")
public class CustomerController extends GenericController<Customer> {


    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerService customerService1) {
        super(customerService);
        this.customerService = customerService1;
    }

    @PostMapping("/orders")
    public ResponseEntity<Void> order(@RequestBody OrderRequest request) {
        customerService.order(request);
        return ResponseEntity.ok().build();
    }
}
