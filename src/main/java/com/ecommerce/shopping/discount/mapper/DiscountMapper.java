package com.ecommerce.shopping.discount.mapper;

import com.ecommerce.shopping.discount.dto.DiscountRequest;
import com.ecommerce.shopping.discount.dto.DiscountResponse;
import com.ecommerce.shopping.discount.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountMapper {

    public DiscountResponse mapDiscountToDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .discountId(discount.getDiscountId())
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .isActive(discount.getIsActive())
                .build();
    }

    public Discount mapDiscountRequestToDiscount(DiscountRequest discountRequest, Discount discount){
        discount.setDiscountValue(discountRequest.getDiscountValue());
        discount.setDiscountType(discountRequest.getDiscountType());
        discount.setActive(discount.getIsActive());
        return discount;
    }
}
