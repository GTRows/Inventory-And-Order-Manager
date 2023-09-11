package com.gtrows.DistributorOrderSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtrows.DistributorOrderSystem.TestConfig;
import com.gtrows.DistributorOrderSystem.model.*;
import com.gtrows.DistributorOrderSystem.repository.CustomerRepository;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.ProductRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private TestConfig testConfig;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    public void setUp() {
        warehouseRepository.deleteAll();
        distributorRepository.deleteAll();
        testConfig.initWarehouse(warehouseRepository);
        testConfig.initDistributors(distributorRepository);
    }


    @Test
    public void testAddProductToWarehouse() throws Exception {
        ArrayList<StoredProduct> originalProducts = getProductsInStock();

        ArrayList<StoredProduct> beforeStoredProducts = new ArrayList<>();
        for (StoredProduct product : originalProducts) {
            beforeStoredProducts.add(product.clone());
        }

        mockMvc.perform(post("/api/warehouses/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoredProduct("1", 10))))
                .andExpect(status().isOk());

        ArrayList<StoredProduct> afterStoredProducts = getProductsInStock();
        Assertions.assertNotEquals(beforeStoredProducts, afterStoredProducts,
                "The products before and after addition should not be exactly the same.");
    }


    @Test
    public void testRemoveProductFromWarehouse() throws Exception {
        ArrayList<StoredProduct> beforeStoredProducts = getDeepCopyOfProducts();

        mockMvc.perform(delete("/api/warehouses/products/1"))
                .andExpect(status().isNoContent());

        ArrayList<StoredProduct> afterStoredProducts = getProductsInStock();
        Assertions.assertNotEquals(beforeStoredProducts, afterStoredProducts,
                "The products before and after deletion should not be exactly the same.");
    }

    @Test
    public void testUpdateStockInWarehouse() throws Exception {
        Integer newQuantity = 20;
        mockMvc.perform(put("/api/warehouses/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newQuantity)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateAndProductToWarehouse() throws Exception {
        ArrayList<StoredProduct> beforeStoredProducts = getDeepCopyOfProducts();
        Product product = new Product("10", 10.0);
        StoredProduct storedProduct = new StoredProduct(product.getId(), 100);
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(product, storedProduct);

        mockMvc.perform(post("/api/warehouses/create-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isCreated());

        ArrayList<StoredProduct> afterStoredProducts = getProductsInStock();
        Assertions.assertNotEquals(beforeStoredProducts, afterStoredProducts,
                "The products before and after addition should not be exactly the same.");
    }

    private ArrayList<StoredProduct> getDeepCopyOfProducts() throws JsonProcessingException {
        ArrayList<StoredProduct> originalProducts = getProductsInStock();
        String serializedProducts = objectMapper.writeValueAsString(originalProducts);
        return objectMapper.readValue(serializedProducts, new TypeReference<ArrayList<StoredProduct>>() {
        });
    }


    private ArrayList<StoredProduct> getProductsInStock() {
        return (ArrayList<StoredProduct>) Warehouse.getInstance().getStoredProducts();
    }
}
