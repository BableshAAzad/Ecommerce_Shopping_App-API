package com.ecommerce.shopping.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageType {
    private Long storageTypeId;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double capacityWeightInKg;
    private int unitsAvailable;
}
