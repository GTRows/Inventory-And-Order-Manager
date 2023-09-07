package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void testGetAll() {
        productController.getAll();
        verify(productService, times(1)).getAll();
    }

    @Test
    public void testGetById() {
        String testId = "testId";
        productController.getById(testId);
        verify(productService, times(1)).getById(testId);
    }

    @Test
    public void testCreate() {
        Product product = new Product();
        productController.create(product);
        verify(productService, times(1)).save(product);
    }

    @Test
    public void testDelete() {
        String testId = "testId";
        Product product = new Product();
        product.setId(testId);
        when(productService.getById(testId)).thenReturn(java.util.Optional.of(product));
        productController.delete(testId);

        verify(productService, times(1)).delete(testId);
    }

    @Test
    public void testPut() {
        String testId = "testId";
        Product product = new Product();
        product.setId(testId);
        when(productService.getById(testId)).thenReturn(java.util.Optional.of(product));
        productController.update(testId, product);

        verify(productService, times(1)).save(product);
    }
}

