package com.ecommerce.shopping.order.controller;

import com.ecommerce.shopping.config.WebClientProvider;
import com.ecommerce.shopping.order.dto.OrderRequest;
import com.ecommerce.shopping.order.dto.OrderResponseDto;
import com.ecommerce.shopping.order.service.OrderService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final WebClientProvider webClientProvider;

    @PostMapping("/customers/{customerId}/addresses/{addressId}/products/{productId}/purchase-orders")
    public Mono<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            @RequestBody OrderRequest orderRequest,
            @PathVariable Long productId,
            @PathVariable Long customerId,
            @PathVariable Long addressId) {
        return orderService.generatePurchaseOrder(orderRequest, productId, customerId, addressId);
    }

    @GetMapping("/customers/{customerId}/purchase-orders")
    public Mono<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(
            @PathVariable Long customerId) {
        return webClientProvider.getPurchaseOrders(customerId);
    }

    @GetMapping("/customers/purchase-orders/{orderId}")
    public Mono<byte[]> getOrderInvoice(
            @PathVariable Long orderId) {
        return webClientProvider.getOrderInvoice(orderId);
    }
}

