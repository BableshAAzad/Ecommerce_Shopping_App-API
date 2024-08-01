package com.ecommerce.shopping.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InventorySearchCriteria {
    private String productTitle;
    private List<String> materialTypes;
    private String description;
    private Double minPrice;
    private Double maxPrice;
    private String sortOrder;
}