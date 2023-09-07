package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class WarehouseServiceTest {

    private WarehouseService warehouseService;
    private ProductRepository productRepository;
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        warehouseRepository = mock(WarehouseRepository.class);
        warehouseService = new WarehouseService(warehouseRepository, productRepository);
    }

    @Test
    public void testAddProductToWarehouse() {
        String productId = "testId";
        int quantity = 10;
        Product mockProduct = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        warehouseService.addProductToWarehouse(productId, quantity);

        verify(productRepository, times(1)).findById(productId);
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

}
