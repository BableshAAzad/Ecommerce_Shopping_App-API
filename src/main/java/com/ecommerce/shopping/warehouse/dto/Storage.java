package com.ecommerce.shopping.warehouse.dto;

import com.ecommerce.shopping.enums.MaterialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    private Long storageId;
    private String blockName;
    private String section;
    private List<MaterialType> materialTypes;
    private Double maxAdditionalWeightInKg;
    private Double availableArea;
    private Long storeHouseId;
    private Long sellerId;
}
