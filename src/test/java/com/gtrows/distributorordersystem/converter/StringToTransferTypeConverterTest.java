package com.gtrows.DistributorOrderSystem.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.gtrows.DistributorOrderSystem.enums.TransferType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringToTransferTypeConverterTest {

    private StringToTransferTypeConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new StringToTransferTypeConverter();
    }

    @Test
    public void testConvert_withValidInput() {
        TransferType result = converter.convert("MAIN_DISTRIBUTOR");
        assertEquals(TransferType.MAIN_DISTRIBUTOR, result);

        result = converter.convert("main_distributor");
        assertEquals(TransferType.MAIN_DISTRIBUTOR, result);
    }

    @Test
    public void testConvert_withInvalidInput() {
        TransferType result = converter.convert("INVALID_TYPE");
        assertNull(result);
    }
}

