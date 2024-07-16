package com.ecommerce.shopping.warehouse.dto;

import java.util.List;

public class Storage {
    private Long storageId;
    private String blockName;
    private String section;
    private List<StorageType> materialTypes;
    private Double maxAdditionalWeightInKg;
    private Double availableArea;
}
