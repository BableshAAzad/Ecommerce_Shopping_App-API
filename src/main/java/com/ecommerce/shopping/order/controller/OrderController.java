package com.ecommerce.shopping.order.controller;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.order.dto.OrderRequest;
import com.ecommerce.shopping.order.dto.OrderResponseDto;
import com.ecommerce.shopping.order.service.OrderService;
import com.ecommerce.shopping.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "https://ecommerce.bableshaazad.com")
public class OrderController {

    private final OrderService orderService;
    private final RestTemplateProvider restTemplateProvider;

    @PostMapping("/customers/{customerId}/addresses/{addressId}/products/{productId}/purchase-orders")
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            @RequestBody OrderRequest orderRequest,
            @PathVariable Long productId,
            @PathVariable Long customerId,
            @PathVariable Long addressId) {
        return orderService.generatePurchaseOrder(orderRequest, productId, customerId, addressId);
    }

    @GetMapping("/customers/{customerId}/purchase-orders")
    public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(
            @PathVariable Long customerId) {
        return restTemplateProvider.getPurchaseOrders(customerId);
    }

    @GetMapping("/customers/purchase-orders/{orderId}")
    public ResponseEntity<byte[]> getOrderInvoice(
            @PathVariable Long orderId) {
        return restTemplateProvider.getOrderInvoice(orderId);
    }
}

