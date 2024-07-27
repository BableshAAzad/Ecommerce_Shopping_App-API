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
public class StorageRequest {
    private String blockName;
    private String section;
    private List<MaterialType> materialTypes;
}
