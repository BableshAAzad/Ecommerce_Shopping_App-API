package com.ecommerce.shopping.address.mapper;

import com.ecommerce.shopping.address.dto.AddressRequest;
import com.ecommerce.shopping.address.dto.AddressResponse;
import com.ecommerce.shopping.address.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address mapAddressRequestToAddress(AddressRequest addressRequest, Address address) {
        address.setStreetAddress(addressRequest.getStreetAddress());
        address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setPincode(addressRequest.getPincode());
        address.setAddressType(addressRequest.getAddressType());
        return address;
    }

    public AddressResponse mapAddressToAddressResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .streetAddress(address.getStreetAddress())
                .streetAddressAdditional(address.getStreetAddressAdditional())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pincode(address.getPincode())
                .addressType(address.getAddressType())
                .contacts(address.getContacts())
                .build();
    }

}
