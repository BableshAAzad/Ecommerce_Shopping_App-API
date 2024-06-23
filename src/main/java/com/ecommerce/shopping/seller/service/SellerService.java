package com.ecommerce.shopping.seller.service;

import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SellerService {
    ResponseEntity<ResponseStructure<UserResponse>> findSeller(Long sellerId);

    ResponseEntity<ResponseStructure<List<UserResponse>>> findSellers();
}
