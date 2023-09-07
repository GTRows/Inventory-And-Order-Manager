package com.gtrows.DistributorOrderSystem.service;

import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAll() {
        productService.getAll();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        String testId = "testId";
        productService.getById(testId);
        verify(productRepository, times(1)).findById(testId);
    }

    @Test
    public void testSave() {
        Product product = new Product();
        productService.save(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDelete() {
        String testId = "testId";
        productService.delete(testId);
        verify(productRepository, times(1)).deleteById(testId);
    }
}
