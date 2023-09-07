package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DistributorServiceTest {

    private DistributorService distributorService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DistributorRepository distributorRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        distributorService = new DistributorService(distributorRepository, productRepository, distributorRepository, warehouseRepository, null);
    }

    @Test
    public void testTransferStockToMainDistributor_DistributorNotFound() {
        when(distributorRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferStockToMainDistributor("invalidId");
        });
    }

    @Test
    public void testTransferProduct_ProductNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferProduct(null, null, null, null, "invalidProductId", 0);
        });
    }

    @Test
    public void testTransferStockToMainDistributor_MainDistributorNotFound() {
        when(distributorRepository.findById("0")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferStockToMainDistributor("someId");
        });
    }

    @Test
    public void testTransferProduct_SourceDistributorNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));
        when(distributorRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferProduct(TransferType.MAIN_DISTRIBUTOR, "invalidId", TransferType.SUB_DISTRIBUTOR, "someId", "someProductId", 10);
        });
    }

    @Test
    public void testTransferProduct_TargetDistributorNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));
        when(distributorRepository.findById("sourceId")).thenReturn(Optional.of(new Distributor()));
        when(distributorRepository.findById("targetId")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferProduct(TransferType.MAIN_DISTRIBUTOR, "sourceId", TransferType.SUB_DISTRIBUTOR, "targetId", "someProductId", 10);
        });
    }

    @Test
    public void testTransferProduct_ProductNotInStock() {
        Distributor distributor = new Distributor();
        distributor.setProductsInStock(new ArrayList<>());

        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));
        when(distributorRepository.findById(any())).thenReturn(Optional.of(distributor));

        assertThrows(IllegalArgumentException.class, () -> {
            distributorService.transferProduct(TransferType.MAIN_DISTRIBUTOR, "sourceId", TransferType.SUB_DISTRIBUTOR, "targetId", "someProductIdNotInStock", 10);
        });
    }

}

