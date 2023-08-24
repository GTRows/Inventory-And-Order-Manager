package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Customer;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends GenericService<Customer> {

    @Autowired
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}
