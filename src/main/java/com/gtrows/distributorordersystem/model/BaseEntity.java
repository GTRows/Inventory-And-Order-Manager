package com.gtrows.DistributorOrderSystem.model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class BaseEntity {
    @Id
    @Setter
    private String id;
}
