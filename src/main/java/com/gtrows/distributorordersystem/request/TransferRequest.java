package com.gtrows.DistributorOrderSystem.request;

import com.gtrows.DistributorOrderSystem.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private TransferType sourceType;
    private String sourceId;
    private TransferType targetType;
    private String targetId;
    private String productId;
    private int quantity;
}