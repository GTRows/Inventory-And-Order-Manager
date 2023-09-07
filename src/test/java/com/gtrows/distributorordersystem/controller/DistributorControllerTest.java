package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Distributor;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import com.gtrows.DistributorOrderSystem.request.ProductTransferRequest;
import com.gtrows.DistributorOrderSystem.service.DistributorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DistributorControllerTest {

    private DistributorController distributorController;
    private DistributorService distributorService;

    @BeforeEach
    public void setUp() {
        distributorService = mock(DistributorService.class);
        distributorController = new DistributorController(distributorService);
    }

    @Test
    public void testCreate() {
        Distributor distributor = new Distributor();
        distributorController.create(distributor);

        verify(distributorService, times(1)).save(distributor);
    }


    @Test
    public void testDelete() {
        String id = "testId";
        Distributor distributor = new Distributor();
        distributor.setId(id);
        when(distributorService.getById(id)).thenReturn(java.util.Optional.of(distributor));
        distributorController.delete(id);

        verify(distributorService, times(1)).getById(id);
        verify(distributorService, times(1)).transferStockToMainDistributor(id);
        verify(distributorService, times(1)).delete(id);
    }


    @Test
    public void testTransfer() {
        TransferType sourceType = TransferType.MAIN_DISTRIBUTOR;
        String sourceId = "sourceId";
        TransferType targetType = TransferType.SUB_DISTRIBUTOR;
        String targetId = "targetId";
        ProductTransferRequest request = new ProductTransferRequest();
        distributorController.transfer(sourceType, sourceId, targetType, targetId, request);

        verify(distributorService, times(1)).transferProduct(sourceType, sourceId, targetType, targetId, request.getProductId(), request.getQuantity());
    }

}

