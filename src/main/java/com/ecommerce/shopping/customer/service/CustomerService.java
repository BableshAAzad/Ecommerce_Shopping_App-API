package com.ecommerce.shopping.customer.service;

import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<ResponseStructure<UserResponse>> findCustomer(Long customerId);

    ResponseEntity<ResponseStructure<List<UserResponse>>> findCustomers();
//    Demo commit add for testing
}
