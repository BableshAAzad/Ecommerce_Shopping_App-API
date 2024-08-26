package com.ecommerce.shopping.config;

import com.ecommerce.shopping.order.dto.OrderRequestDto;
import com.ecommerce.shopping.order.dto.OrderResponseDto;
import com.ecommerce.shopping.product.dto.ProductRequestDto;
import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.warehouse.dto.*;
import com.ecommerce.shopping.enums.BaseUrl;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class WebClientProvider {

    private final WebClient webClient;

    @Value("${application.client.api_key}")
    private String apiKey;

    @Value("${application.client.username}")
    private String username;

    @Value("${application.client.client_id}")
    private Long clientId;

    public WebClientProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BaseUrl.BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<Inventory> getProduct(Long productId) {
        return webClient.get()
                .uri("inventories/{productId}", productId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<Inventory>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Inventory>> getProducts(int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("inventories")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Inventory>> searchProducts(String query, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("inventories/search/{query}")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(query))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Inventory>> filterProducts(InventorySearchCriteria inventorySearchCriteria, int page, int size) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("inventories/filter")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .bodyValue(inventorySearchCriteria)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Inventory>> getProductsBySellerId(Long sellerId, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("inventories/sellers/{sellerId}")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(sellerId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<ProductResponse> addProduct(Long storageId, int quantity, ProductRequestDto productRequestDto) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("clients/1/storages/{storageId}/inventories")
                        .queryParam("quantity", quantity)
                        .build(storageId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .bodyValue(productRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<ProductResponse>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<ProductResponse> updateProduct(Long productId, int quantity, ProductRequestDto productRequestDto) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path("clients/inventories/{productId}/stocks")
                        .queryParam("quantity", quantity)
                        .build(productId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .bodyValue(productRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<ProductResponse>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Storage>> getStoragesBySellerId(Long sellerId, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/sellers/{sellerId}/storages")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(sellerId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Storage>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Storage>> getStoragesByWareHouseId(Long wareHouseId, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/storageHouses/{wareHouseId}/storages")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(wareHouseId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Storage>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Map<String, Object>>> getWareHousesByCity(
            String city, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/cities/{city}/storehouses")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(city))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Map<String, Object>>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<PagedModel<Map<String, Object>>> getStoreHousesWithAddressForClient(
            int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/{clientId}/storehouses")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build(clientId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<PagedModel<Map<String, Object>>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<OrderResponseDto> generatePurchaseOrder(OrderRequestDto orderRequestDto, Long productId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("clients/inventories/{productId}/purchase-orders")
                        .build(productId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .bodyValue(orderRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<OrderResponseDto>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseStructure<List<OrderResponseDto>> getPurchaseOrders(Long customerId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/purchase-orders/customers/{customerId}")
                        .build(customerId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ResponseStructure<List<OrderResponseDto>>>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    public byte[] getOrderInvoice(Long orderId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("clients/purchase-orders/invoice/{orderId}")
                        .build(orderId))
                .header("API-KEY", apiKey)
                .header("USERNAME", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<byte[]>() {})
                .block();
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
}
