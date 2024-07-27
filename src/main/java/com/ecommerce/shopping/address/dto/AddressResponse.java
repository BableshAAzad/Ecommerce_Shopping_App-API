package com.ecommerce.shopping.address.dto;

import com.ecommerce.shopping.contact.entity.Contact;
import com.ecommerce.shopping.enums.AddressType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private Long addressId;
    private String streetAddress;
    private String streetAddressAdditional;
    private String city;
    private String state;
    private String country;
    private int pincode;
    private AddressType addressType;
    private List<Contact> contacts;
}
