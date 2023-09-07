package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import com.gtrows.DistributorOrderSystem.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    private CustomerController customerController;
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService, customerService);
    }

    @Test
    public void testOrder() {
        OrderRequest orderRequest = new OrderRequest();
        customerController.order(orderRequest);

        verify(customerService, times(1)).order(orderRequest);
    }

}
