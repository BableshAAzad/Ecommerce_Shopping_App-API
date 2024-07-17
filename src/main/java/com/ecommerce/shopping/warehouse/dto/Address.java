package com.ecommerce.shopping.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
    private String longitude;
    private String latitude;
}
