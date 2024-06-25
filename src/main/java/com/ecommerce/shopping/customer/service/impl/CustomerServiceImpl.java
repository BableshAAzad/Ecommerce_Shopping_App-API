package com.ecommerce.shopping.customer.service.impl;

import com.ecommerce.shopping.customer.repository.CustomerRepository;
import com.ecommerce.shopping.customer.service.CustomerService;
import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.mapper.UserMapper;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

      private final CustomerRepository customerRepository;

      private final UserMapper userMapper;
    //------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> findCustomer(Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Customer Founded")
                    .setData(userMapper.mapUserToUserResponse(customer)));
        }).orElseThrow(() -> new UserNotExistException("CustomerId : " + customerId + ", is not exist"));
    }
    //------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findCustomers() {
        List<UserResponse> customerResponseList = customerRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<UserResponse>>()
                .setMessage("Customers are Founded")
                .setData(customerResponseList));
    }
    //------------------------------------------------------------------------------------------------------------------------

}
