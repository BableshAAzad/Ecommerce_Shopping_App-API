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
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RestTemplateProvider {

   private final RestTemplate restTemplate;

    @Value("${application.client.api_key}")
    private String apiKey;

    @Value("${application.client.username}")
    private String username;

    @Value("${application.client.client_id}")
    private Long clientId;

   public RestTemplateProvider(RestTemplate restTemplate){
       this.restTemplate = restTemplate;
   }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<Inventory>> getProduct(Long productId) {
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "inventories/" + productId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<Inventory>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Inventory>>> getProducts(int page, int size) {
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "inventories?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/search/criteria?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Inventory>>> searchProducts(String query, int page, int size) {
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "inventories/search/" + query + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/filter?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Inventory>>> filterProducts(
            InventorySearchCriteria inventorySearchCriteria, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InventorySearchCriteria> requestEntity = new HttpEntity<>(inventorySearchCriteria, headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "inventories/filter" + "?page=" + page + "&size=" + size,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/sellers/sellerId?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Inventory>>> getProductsBySellerId(
            Long sellerId, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "inventories/sellers/" + sellerId + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Inventory>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
            Long storageId,
            int quantity,
            ProductRequestDto productRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequestDto, headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/1/storages/" + storageId + "/inventories?quantity=" + quantity,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ResponseStructure<ProductResponse>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(
            Long productId,
            int quantity,
            ProductRequestDto productRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequestDto> entity = new HttpEntity<>(productRequestDto, headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/inventories/" + productId + "/stocks?quantity=" + quantity,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<ResponseStructure<ProductResponse>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Storage>>> getStoragesBySellerId(
            Long sellerId, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/sellers/" + sellerId + "/storages?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Storage>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Storage>>> getStoragesByWareHouseId(
            Long wareHouseId, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/storageHouses/" + wareHouseId + "/storages?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Storage>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> getWareHousesByCity(
            String city, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/cities/" + city + "/storehouses?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Map<String, Object>>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> getStoreHousesWithAddressForClient(
            int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/" + clientId + "/storehouses?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Map<String, Object>>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            OrderRequestDto orderRequestDto, Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderRequestDto> entity = new HttpEntity<>(orderRequestDto, headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/inventories/" + productId + "/purchase-orders",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ResponseStructure<OrderResponseDto>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> getPurchaseOrders(Long customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                BaseUrl.BASE_URL + "clients/purchase-orders/customers/" + customerId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<OrderResponseDto>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<byte[]> getOrderInvoice(Long orderId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(BaseUrl.BASE_URL + "clients/purchase-orders/invoice/" + orderId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<byte[]>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
}

