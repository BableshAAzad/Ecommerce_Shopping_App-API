package com.ecommerce.shopping.address.service;

import com.ecommerce.shopping.address.dto.AddressRequest;
import com.ecommerce.shopping.address.dto.AddressResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, Long userId);

    ResponseEntity<ResponseStructure<List<AddressResponse>>> getAddress(Long userId);

    ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(Long addressId, AddressRequest addressRequest);
}
