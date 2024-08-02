package com.ecommerce.shopping.config;

import com.ecommerce.shopping.order.dto.OrderRequestDto;
import com.ecommerce.shopping.order.dto.OrderResponseDto;
import com.ecommerce.shopping.warehouse.dto.*;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RestTemplateProvider {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${application.client.api_key}")
    private String apiKey;

    @Value("${application.client.username}")
    private String username;

    @Value("${application.client.client_id}")
    private Long clientId;

    //---------------------------------------------------------------------------------------------------
    public Product getProduct(Long productId) {
        ResponseEntity<ResponseStructure<Product>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/" + productId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<Product>>() {
                }
        );
        ResponseStructure<Product> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> getProducts(int page, int size) {
        return restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Product>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/search/criteria?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> searchProducts(String query, int page, int size) {
        return restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/search/" + query + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Product>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/filter?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> filterProducts(
            InventorySearchCriteria inventorySearchCriteria, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InventorySearchCriteria> requestEntity = new HttpEntity<>(inventorySearchCriteria, headers);
        return restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/filter" + "?page=" + page + "&size=" + size,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Product>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //inventories/sellers/sellerId?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<Product>>> getProductsBySellerId(
            Long sellerId, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/sellers/" + sellerId + "?page=" + page + "&size=" + size,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<PagedModel<Product>>>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    public Product addProduct(Long storageId, int quantity, ProductRequest productRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequest> entity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ResponseStructure<Product>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/clients/1/storages/" + storageId + "/inventories?quantity=" + quantity,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ResponseStructure<Product>>() {
                }
        );
        ResponseStructure<Product> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public Product updateProduct(Long productId, ProductRequest productRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProductRequest> entity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<ResponseStructure<Product>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/clients/inventories/" + productId,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<ResponseStructure<Product>>() {
                }
        );
        ResponseStructure<Product> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Storage> getStoragesBySellerId(Long sellerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Storage>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/clients/storages/sellers/" + sellerId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Storage>>>() {
                }
        );
        ResponseStructure<List<Storage>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Storage> getStoragesByWareHouseId(Long wareHouseId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Storage>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/clients/storageHouses/" + wareHouseId + "/storages",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Storage>>>() {
                }
        );
        ResponseStructure<List<Storage>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Map<String, Object>> getWareHousesByCity(String city) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Map<String, Object>>>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/client/cities/" + city + "/storehouses",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Map<String, Object>>>>() {
                }
        );
        ResponseStructure<List<Map<String, Object>>> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Map<String, Object>> getStoreHousesWithAddressForClient() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("API-KEY", apiKey);
            headers.set("USERNAME", username);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ResponseStructure<List<Map<String, Object>>>> responseEntity = restTemplate.exchange(
                    "http://localhost:8081/api/v1/clients/" + clientId + "/storehouses",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ResponseStructure<List<Map<String, Object>>>>() {
                    }
            );

            ResponseStructure<List<Map<String, Object>>> responseStructure = responseEntity.getBody();
            return responseStructure.getData();
        } catch (UnknownContentTypeException ex) {
            // Log the error and handle the HTML response
            System.err.println("Unexpected content type: " + ex.getMessage());
            // Handle the response as needed
            return Collections.emptyList();
        }
    }

    //---------------------------------------------------------------------------------------------------
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(OrderRequestDto orderRequestDto, Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderRequestDto> entity = new HttpEntity<>(orderRequestDto, headers);
        return restTemplate.exchange(
                "http://localhost:8081/api/v1/clients/inventories/" + productId + "/purchase-orders",
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
                "http://localhost:8081/api/v1/clients/purchase-orders/customers/" + customerId,
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
        return restTemplate.exchange("http://localhost:8081/api/v1/clients/purchase-orders/invoice/" + orderId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<byte[]>() {
                }
        );
    }

    //---------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
}

