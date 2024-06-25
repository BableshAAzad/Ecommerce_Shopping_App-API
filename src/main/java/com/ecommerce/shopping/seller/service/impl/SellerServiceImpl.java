package com.ecommerce.shopping.seller.service.impl;

import com.ecommerce.shopping.exception.UserNotExistException;
import com.ecommerce.shopping.seller.repository.SellerRepository;
import com.ecommerce.shopping.seller.service.SellerService;
import com.ecommerce.shopping.user.dto.UserResponse;
import com.ecommerce.shopping.user.mapper.UserMapper;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<ResponseStructure<UserResponse>> findSeller(Long sellerId) {
        return sellerRepository.findById(sellerId).map(seller -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<UserResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Seller Founded")
                    .setData(userMapper.mapUserToUserResponse(seller)));
        }).orElseThrow(() -> new UserNotExistException("SellerId : " + sellerId + ", is not exist"));
    }

    @Override
    public ResponseEntity<ResponseStructure<List<UserResponse>>> findSellers() {
        List<UserResponse> sellerResponseList = sellerRepository.findAll()
                .stream()
                .map(userMapper::mapUserToUserResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<UserResponse>>()
                .setMessage("Sellers are Founded")
                .setData(sellerResponseList));
    }
}
