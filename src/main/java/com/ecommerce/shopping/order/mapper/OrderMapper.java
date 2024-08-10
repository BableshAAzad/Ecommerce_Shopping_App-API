package com.ecommerce.shopping.order.mapper;

import com.ecommerce.shopping.order.dto.OrderRequest;
import com.ecommerce.shopping.order.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order mapOrderRequestDtoToOrder(OrderRequest orderRequestDto, Order order){
        order.setTotalQuantity(orderRequestDto.getTotalQuantity());
        order.setTotalPrice(orderRequestDto.getTotalPrice());
        order.setDiscountPrice(orderRequestDto.getDiscountPrice());
        order.setDiscount(orderRequestDto.getDiscount());
        order.setTotalPayableAmount(orderRequestDto.getTotalPayableAmount());
        return order;
    }

}
