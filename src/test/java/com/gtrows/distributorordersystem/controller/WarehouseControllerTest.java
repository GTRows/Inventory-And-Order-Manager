package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.CreateOrderRequest;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.service.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class WarehouseControllerTest {

    private WarehouseController warehouseController;
    private WarehouseService warehouseService;

    @BeforeEach
    public void setUp() {
        warehouseService = mock(WarehouseService.class);
        warehouseController = new WarehouseController(warehouseService);
    }

    @Test
    public void testAddProductToWarehouse_StoredProduct() {
        StoredProduct storedProduct = new StoredProduct();
        warehouseController.addProductToWarehouse(storedProduct);

        verify(warehouseService, times(1)).addProductToWarehouse(storedProduct.getProductId(), storedProduct.getQuantity());
    }

    @Test
    public void testAddProductToWarehouse_CreateOrderRequest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        warehouseController.addProductToWarehouse(createOrderRequest);

        verify(warehouseService, times(1)).createAndProductToWarehouse(createOrderRequest);
    }

    @Test
    public void testRemoveProductFromWarehouse() {
        String testId = "testId";
        warehouseController.removeProductFromWarehouse(testId);

        verify(warehouseService, times(1)).removeProductFromWarehouse(testId);
    }

    @Test
    public void testUpdateStockInWarehouse() {
        String testId = "testId";
        int newQuantity = 10;
        warehouseController.updateStockInWarehouse(testId, newQuantity);

        verify(warehouseService, times(1)).updateStockInWarehouse(testId, newQuantity);
    }
}

