package com.gtrows.DistributorOrderSystem.converter;

import org.springframework.core.convert.converter.Converter;
import com.gtrows.DistributorOrderSystem.enums.TransferType;
import org.springframework.stereotype.Component;

@Component
public class StringToTransferTypeConverter implements Converter<String, TransferType> {
    @Override
    public TransferType convert(String source) {
        try {
            return TransferType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

