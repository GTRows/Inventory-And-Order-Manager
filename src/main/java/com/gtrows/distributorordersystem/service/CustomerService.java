package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import com.gtrows.DistributorOrderSystem.request.OrderRequest;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService extends GenericService<Customer> {

    private final ProductRepository productRepository;

    private final DistributorRepository distributorRepository;

    private final CustomerRepository customerRepository;

    private final DistributorService distributorService;

    private final WarehouseRepository warehouseRepository;

    @Autowired
    public CustomerService(DistributorService distributorService, CustomerRepository repository, ProductRepository productRepository, DistributorRepository distributorRepository, CustomerRepository customerRepository, WarehouseRepository warehouseRepository) {
        super(repository);
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.distributorRepository = distributorRepository;
        this.customerRepository = customerRepository;
        this.distributorService = distributorService;
    }

    public void order(OrderRequest request) {
        // Product control
        productRepository.findById(request.getOrder().getProductId())
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

        // Check if the product is in stock or not
        if (storedProductOpt.isEmpty() || storedProductOpt.get().getQuantity() < request.getOrder().getQuantity()) {
            Distributor supplyingDistributor = findDistributorWithMostOfProduct(request.getOrder().getProductId());

            if (supplyingDistributor == null) {
                throw new IllegalArgumentException("Product is not in stock in any of the SUB Distributors");
            }

            // Transfer product from one distributor to another using their IDs
            transferProductFromDistributorToDistributor(supplyingDistributor.getId(), distributor.getId(), request.getOrder().getProductId(), request.getOrder().getQuantity());
        } else {
            storedProductOpt.get().setQuantity(storedProductOpt.get().getQuantity() - request.getOrder().getQuantity());
        }

        // Save distributor
        distributorService.save(distributor);

        // Add order to customer
        if (customer.getOrders() == null) {
            customer.setOrders(new ArrayList<>());
        }
        Order order = new Order();
        order.setProductId(request.getOrder().getProductId());
        order.setQuantity(request.getOrder().getQuantity());
        customer.getOrders().add(order);
        super.save(customer);
    }

    public Distributor findDistributorWithMostOfProduct(String productId) {
        List<Distributor> allDistributors = distributorRepository.findAll();
        List<Distributor> subDistributors = allDistributors.stream()
                .filter(d -> !d.getId().equals("0"))
                .toList();
        return subDistributors.stream()
                .filter(d -> d.getProductsInStock().stream().anyMatch(p -> p.getProductId().equals(productId)))
                .max(Comparator.comparingInt(d -> d.getProductsInStock().stream().filter(p -> p.getProductId().equals(productId)).findFirst().get().getQuantity()))
                .orElse(null);
    }

    public void transferProductFromDistributorToDistributor(String sourceDistributorId, String targetDistributorId, String productId, int quantity) {
        WarehouseService warehouseService = new WarehouseService(warehouseRepository, productRepository);
        DistributorService transferService = new DistributorService(distributorRepository, productRepository, warehouseRepository, warehouseService);
        transferService.transferProduct(TransferType.SUB_DISTRIBUTOR, sourceDistributorId, TransferType.SUB_DISTRIBUTOR, targetDistributorId, productId, quantity);
    }


}
