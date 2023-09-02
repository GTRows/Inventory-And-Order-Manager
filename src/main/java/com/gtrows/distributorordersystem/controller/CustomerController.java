package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Customer;
import com.gtrows.DistributorOrderSystem.service.CustomerService;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController extends GenericController<Customer> {

    @Autowired
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> order(@RequestBody OrderRequest request){
        customerService.order(request);
        return ResponseEntity.ok().build();
    }
}
