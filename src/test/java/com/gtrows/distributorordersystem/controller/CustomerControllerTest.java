package com.gtrows.DistributorOrderSystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DistributorService distributorService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Customer())))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = customerRepository.findByName("Customer 1");
        String customerId = customer.getId();

        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customer)));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer("beforeUpdate", null);
        customerRepository.save(customer);

        customer.setName("afterUpdate");


        mockMvc.perform(put("/api/customers/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/{id}", customerRepository.findByName("3").getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Get customer Id by name == "Customer 1"
        Customer customer = customerRepository.findByName("Customer 1");
        String customerId = customer.getId();

        // Get product Id by name == "P1"
        Product product = productRepository.findByName("P1");
        String productId = product.getId();

        // Get distributor Id randomly withot id == "0"
        Distributor distributor = distributorRepository.findAll().stream()
                .filter(d -> !d.getId().equals("0"))
                .findAny()
                .orElse(null);
        String distributorId = distributor.getId();
        distributorService.updateProductList(new ArrayList<>(), productId, 10);
        distributorService.save(distributor);


        // Create order request
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerId(customerId);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(1);
        orderRequest.setOrder(order);
        orderRequest.setDistributorId(distributorId);



        mockMvc.perform(post("/api/customers/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }
}
