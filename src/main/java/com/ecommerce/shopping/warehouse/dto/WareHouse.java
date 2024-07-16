package com.ecommerce.shopping.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouse {
    private Long storeHouseId;
    private String storeHoseName;
    private Double totalCapacityInKg;
}
