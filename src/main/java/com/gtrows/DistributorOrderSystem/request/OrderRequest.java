package com.gtrows.DistributorOrderSystem.request;

import com.gtrows.DistributorOrderSystem.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String customerId;
    private Order order;
    private String distributorId;
}
