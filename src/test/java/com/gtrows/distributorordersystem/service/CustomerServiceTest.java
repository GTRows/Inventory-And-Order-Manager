package com.gtrows.DistributorOrderSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DistributorRepository distributorRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DistributorService distributorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOrder_withValidRequest() {
        OrderRequest orderRequest = new OrderRequest("customer1", new Order("product1", 5), "distributor1");

        Product mockProduct = new Product();
        Distributor mockDistributor = new Distributor();
        mockDistributor.setProductsInStock(List.of(new StoredProduct("product1", 10)));
        Customer mockCustomer = new Customer();

        when(productRepository.findById("product1")).thenReturn(Optional.of(mockProduct));
        when(distributorRepository.findById("distributor1")).thenReturn(Optional.of(mockDistributor));
        when(customerRepository.findById("customer1")).thenReturn(Optional.of(mockCustomer));

        customerService.order(orderRequest);

        verify(productRepository).findById("product1");
        verify(distributorRepository).findById("distributor1");
        verify(customerRepository).findById("customer1");
        verify(distributorService).save(mockDistributor);
        verify(customerRepository).save(mockCustomer);
    }

    @Test
    public void testOrder_withInvalidProduct() {
        OrderRequest orderRequest = new OrderRequest("customer1", new Order("invalidProduct", 5), "distributor1");

        when(productRepository.findById("invalidProduct")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.order(orderRequest));
        assertEquals("Invalid product Id:invalidProduct", exception.getMessage());
    }

    @Test
    public void testOrder_withInvalidDistributor() {
        OrderRequest orderRequest = new OrderRequest("customer1", new Order("product1", 5), "invalidDistributor");

        Product mockProduct = new Product();

        when(productRepository.findById("product1")).thenReturn(Optional.of(mockProduct));
        when(distributorRepository.findById("invalidDistributor")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.order(orderRequest));
        assertEquals("Invalid distributor Id:invalidDistributor", exception.getMessage());
    }

    @Test
    public void testOrder_withInvalidCustomer() {
        OrderRequest orderRequest = new OrderRequest("invalidCustomer", new Order("product1", 5), "distributor1");

        Product mockProduct = new Product();
        Distributor mockDistributor = new Distributor();

        when(productRepository.findById("product1")).thenReturn(Optional.of(mockProduct));
        when(distributorRepository.findById("distributor1")).thenReturn(Optional.of(mockDistributor));
        when(customerRepository.findById("invalidCustomer")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.order(orderRequest));
        assertEquals("Invalid customer Id:invalidCustomer", exception.getMessage());
    }

    @Test
    public void testOrder_withProductNotInStock() {
        OrderRequest orderRequest = new OrderRequest("customer1", new Order("product1", 5), "distributor1");

        Product mockProduct = new Product();
        Distributor mockDistributor = new Distributor();
        mockDistributor.setProductsInStock(List.of(new StoredProduct("product2", 10))); // Different product in stock
        Customer mockCustomer = new Customer();

        when(productRepository.findById("product1")).thenReturn(Optional.of(mockProduct));
        when(distributorRepository.findById("distributor1")).thenReturn(Optional.of(mockDistributor));
        when(customerRepository.findById("customer1")).thenReturn(Optional.of(mockCustomer));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.order(orderRequest));
        assertEquals("Product is not in stock", exception.getMessage());
    }

    @Test
    public void testOrder_withNotEnoughStock() {
        OrderRequest orderRequest = new OrderRequest("customer1", new Order("product1", 15), "distributor1"); // Ordering 15 items

        Product mockProduct = new Product();
        Distributor mockDistributor = new Distributor();
        mockDistributor.setProductsInStock(List.of(new StoredProduct("product1", 10))); // Only 10 in stock
        Customer mockCustomer = new Customer();

        when(productRepository.findById("product1")).thenReturn(Optional.of(mockProduct));
        when(distributorRepository.findById("distributor1")).thenReturn(Optional.of(mockDistributor));
        when(customerRepository.findById("customer1")).thenReturn(Optional.of(mockCustomer));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.order(orderRequest));
        assertEquals("Product is not in stock", exception.getMessage());
    }


}

