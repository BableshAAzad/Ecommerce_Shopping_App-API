package com.ecommerce.shopping.discount.service.impl;

import com.ecommerce.shopping.discount.dto.DiscountRequest;
import com.ecommerce.shopping.discount.dto.DiscountResponse;
import com.ecommerce.shopping.discount.entity.Discount;
import com.ecommerce.shopping.discount.mapper.DiscountMapper;
import com.ecommerce.shopping.discount.repository.DiscountRepository;
import com.ecommerce.shopping.discount.service.DiscountService;
import com.ecommerce.shopping.exception.DiscountNotExistException;
import com.ecommerce.shopping.exception.ProductNotExistException;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.product.repository.ProductRepository;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final DiscountMapper discountMapper;

    @Override
    public ResponseEntity<ResponseStructure<List<DiscountResponse>>> getDiscounts(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotExistException("Product Id : " + productId + ", is not exist"));

        List<Discount> discounts = discountRepository.findByProductAndIsActiveTrue(product);
        List<DiscountResponse> discountResponses = discounts
                .stream()
                .map(discountMapper::mapDiscountToDiscountResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<DiscountResponse>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Discounts are founded")
                .setData(discountResponses));
    }
    //---------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<DiscountResponse>> updateDiscount(
            Long discountId,
            DiscountRequest discountRequest) {

        Discount discount = discountRepository
                .findById(discountId)
                .orElseThrow(() -> new DiscountNotExistException("Discount Id : " + discountId + ", is not exist"));

        discount = discountMapper.mapDiscountRequestToDiscount(discountRequest, discount);
        discount = discountRepository.save(discount);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<DiscountResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Discounts are founded")
                .setData(discountMapper.mapDiscountToDiscountResponse(discount)));
    }
    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}
