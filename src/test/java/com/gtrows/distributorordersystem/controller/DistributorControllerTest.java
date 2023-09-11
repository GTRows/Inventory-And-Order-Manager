package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.TestConfig;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.model.StoredProduct;
import com.gtrows.DistributorOrderSystem.model.Warehouse;
import com.gtrows.DistributorOrderSystem.repository.DistributorRepository;
import com.gtrows.DistributorOrderSystem.repository.WarehouseRepository;
import com.gtrows.DistributorOrderSystem.request.TransferRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class DistributorControllerTest {

    private static final String PRODUCT_ID = "1";

    @Autowired
    private DistributorController distributorController;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private TestConfig testConfig;

    @BeforeEach
    public void setUp() {
        warehouseRepository.deleteAll();
        distributorRepository.deleteAll();
        testConfig.initWarehouse(warehouseRepository);
        testConfig.initDistributors(distributorRepository);
    }

    @Test
    public void testDeleteSubDistributor() {
        String targetSubDistributorId = "1";
        String mainDistributorId = "0";
        ArrayList<StoredProduct> beforeStoredProducts = getProductsInStock(mainDistributorId);

        distributorController.delete(targetSubDistributorId);
        ArrayList<StoredProduct> afterStoredProducts = getProductsInStock(mainDistributorId);

        Assertions.assertTrue(distributorRepository.findById(targetSubDistributorId).isEmpty(),
                "Sub Distributor with ID " + targetSubDistributorId + " should be deleted.");

        Assertions.assertNotEquals(beforeStoredProducts, afterStoredProducts,
                "The products before and after deletion should not be exactly the same.");
    }

    @Test
    public void testSubToSubTransfer() {
        TransferRequest request = createTransferRequest(TransferType.SUB_DISTRIBUTOR, "1",
                TransferType.SUB_DISTRIBUTOR, "2", 5);
        distributorController.transfer(request);

        validateTransfer("1");
    }

    @Test
    public void testSubToMainTransfer() {
        TransferRequest request = createTransferRequest(TransferType.SUB_DISTRIBUTOR, "1",
                TransferType.MAIN_DISTRIBUTOR, "0", 5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> distributorController.transfer(request));
    }

    @Test
    public void testSubToWarehouseTransfer() {
        TransferRequest request = createTransferRequest(TransferType.SUB_DISTRIBUTOR, "1",
                TransferType.WAREHOUSE, "0", 5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> distributorController.transfer(request));
    }

    @Test
    public void testMainToSubTransfer() {
        TransferRequest request = createTransferRequest(TransferType.MAIN_DISTRIBUTOR, "0",
                TransferType.SUB_DISTRIBUTOR, "2", 5);
        distributorController.transfer(request);

        validateTransfer("0");
    }

    @Test
    public void testMainToWarehouseTransfer() {
        TransferRequest request = createTransferRequest(TransferType.MAIN_DISTRIBUTOR, "0",
                TransferType.WAREHOUSE, "0", 5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> distributorController.transfer(request));
    }

    @Test
    public void testWarehouseToSubTransfer() {
        TransferRequest request = createTransferRequest(TransferType.WAREHOUSE, "0",
                TransferType.SUB_DISTRIBUTOR, "2", 5);

        Assertions.assertThrows(IllegalArgumentException.class, () -> distributorController.transfer(request));
    }

    @Test
    public void testWarehouseToMainTransfer() {
        TransferRequest request = createTransferRequest(TransferType.WAREHOUSE, "0",
                TransferType.MAIN_DISTRIBUTOR, "0", 5);
        distributorController.transfer(request);

        StoredProduct sourceStoredProduct = Warehouse.getInstance().getStoredProducts()
                .stream().filter(storedProduct -> storedProduct.getProductId().equals(PRODUCT_ID)).findFirst().get();
        StoredProduct targetStoredProduct = findStoredProductById("0");

        Assertions.assertTrue(targetStoredProduct.getQuantity() == 15
                && targetStoredProduct.getProductId().equals(PRODUCT_ID)
                && sourceStoredProduct.getQuantity() == 5
                && sourceStoredProduct.getProductId().equals(PRODUCT_ID));
    }


    private ArrayList<StoredProduct> getProductsInStock(String distributorId) {
        return (ArrayList<StoredProduct>) Objects.requireNonNull(distributorController.getById(distributorId).getBody()).getProductsInStock();
    }

    private TransferRequest createTransferRequest(TransferType sourceType, String sourceId,
                                                  TransferType targetType, String targetId, int quantity) {
        TransferRequest request = new TransferRequest();
        request.setSourceType(sourceType);
        request.setSourceId(sourceId);
        request.setTargetType(targetType);
        request.setTargetId(targetId);
        request.setProductId(PRODUCT_ID);
        request.setQuantity(quantity);
        return request;
    }

    private void validateTransfer(String sourceId) {
        StoredProduct sourceStoredProduct = findStoredProductById(sourceId);
        StoredProduct targetStoredProduct = findStoredProductById("2");
        Assertions.assertTrue(targetStoredProduct.getQuantity() == 5
                && targetStoredProduct.getProductId().equals(PRODUCT_ID)
                && sourceStoredProduct.getQuantity() == 5
                && sourceStoredProduct.getProductId().equals(PRODUCT_ID));
    }

    private StoredProduct findStoredProductById(String id) {
        return distributorRepository.findById(id).get().getProductsInStock()
                .stream().filter(storedProduct -> storedProduct.getProductId().equals(PRODUCT_ID)).findFirst().get();
    }
}
