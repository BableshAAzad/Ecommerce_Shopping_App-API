package com.ecommerce.shopping.order.service;

import com.ecommerce.shopping.order.dto.OrderRequest;
import com.ecommerce.shopping.order.dto.OrderResponseDto;
import com.ecommerce.shopping.utility.ResponseStructure;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            OrderRequest orderRequestDto,
            Long productId,
            Long customerId,
            Long addressId);
}
