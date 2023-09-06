package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class CustomerService extends GenericService<Customer> {

    private final ProductRepository productRepository;

    private final DistributorRepository distributorRepository;

    private final CustomerRepository customerRepository;

    private final DistributorService distributorService;

    @Autowired
    public CustomerService(DistributorService distributorService, CustomerRepository repository, ProductRepository productRepository, DistributorRepository distributorRepository, CustomerRepository customerRepository) {
        super(repository);
        this.productRepository = productRepository;
        this.distributorRepository = distributorRepository;
        this.customerRepository = customerRepository;
        this.distributorService = distributorService;
    }

    public void order(OrderRequest request) {
        // Product control
        Product product = productRepository.findById(request.getOrder().getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + request.getOrder().getProductId()));

        // Distributor control
        Distributor distributor = distributorRepository.findById(request.getDistributorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid distributor Id:" + request.getDistributorId()));

        // Customer control
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + request.getCustomerId()));

        // Order control
        Optional<StoredProduct> storedProductOpt = distributor.getProductsInStock().stream()
                .filter(p -> p.getProductId().equals(request.getOrder().getProductId()))
                .findFirst();

        if (storedProductOpt.isEmpty()) {
            throw new IllegalArgumentException("Product is not in stock");
        }

        StoredProduct storedProduct = storedProductOpt.get();
        if (storedProduct.getQuantity() < request.getOrder().getQuantity()) {
            throw new IllegalArgumentException("Product is not in stock");
        }

        // Order
        storedProduct.setQuantity(storedProduct.getQuantity() - request.getOrder().getQuantity());

        if (customer.getOrders() == null) {
            customer.setOrders(new ArrayList<>());
        }
        distributorService.save(distributor);

        Order order = new Order();
        order.setProductId(request.getOrder().getProductId());
        order.setQuantity(request.getOrder().getQuantity());
        customer.getOrders().add(order);
        super.save(customer);
    }
}
