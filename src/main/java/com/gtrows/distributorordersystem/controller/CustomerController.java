package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Customer;
import com.gtrows.DistributorOrderSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController extends GenericController<Customer> {

    @Autowired
    public CustomerController(CustomerService customerService) {
        super(customerService);
    }
}
