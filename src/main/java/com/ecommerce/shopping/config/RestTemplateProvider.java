package com.ecommerce.shopping.config;

import com.ecommerce.shopping.warehouse.dto.*;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RestTemplateProvider {

    //    private Logger logger = LoggerFactory.getLogger(RestTemplateProvider.class);
    RestTemplate restTemplate = new RestTemplate();

    @Value("${application.client.api_key}")
    private String apiKey;

    @Value("${application.client.username}")
    private String username;

    @Value("${application.client.client_id}")
    private Long clientId;

    public String getTest() {
        return restTemplate.getForObject("http://localhost:8081/api/v1/test", String.class);
    }

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
    public List<Product> getProducts() {
        ResponseEntity<ResponseStructure<List<Product>>> productsResponse = restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<Product>>>() {
                }
        );
        ResponseStructure<List<Product>> responseStructure = productsResponse.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Product> getProductsBySellerId(Long sellerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Product>>> productsResponse = restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/sellers/" + sellerId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Product>>>() {
                }
        );
        ResponseStructure<List<Product>> responseStructure = productsResponse.getBody();
        return responseStructure.getData();
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
    public WareHouse getWareHouse(Long wareHouseId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<WareHouse>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storehouses/" + wareHouseId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<WareHouse>>() {
                }
        );
        ResponseStructure<WareHouse> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<WareHouse> getWareHouses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseStructure<List<WareHouse>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storehouses",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<WareHouse>>>() {
                }
        );
        ResponseStructure<List<WareHouse>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public StorageType getStorageType(Long storageTypeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<StorageType>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storageTypes/" + storageTypeId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<StorageType>>() {
                }
        );
        ResponseStructure<StorageType> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<StorageType> getStorageTypes() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<StorageType>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storageTypes",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<StorageType>>>() {
                }
        );
        ResponseStructure<List<StorageType>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public Storage getStorage(Long storageId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<Storage>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storages/" + storageId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<Storage>>() {
                }
        );
        ResponseStructure<Storage> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Storage> getStorages() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Storage>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storages",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Storage>>>() {
                }
        );
        ResponseStructure<List<Storage>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Storage> getStoragesBySellerId(Long sellerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Storage>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storages/sellers/" + sellerId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Storage>>>() {
                }
        );
        ResponseStructure<List<Storage>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public Storage addStorage(Long wareHouseId, Long storageTypeId, int noOfStorageUnites, StorageRequest storageRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StorageRequest> entity = new HttpEntity<>(storageRequest, headers);
        ResponseEntity<ResponseStructure<Storage>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storehouses/"+wareHouseId+"/storageTypes/"+storageTypeId+"/storages?no_of_storage_units="+noOfStorageUnites,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ResponseStructure<Storage>>() {
                }
        );
        ResponseStructure<Storage> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }
    //---------------------------------------------------------------------------------------------------
    public Address getAddress(Long addressId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<Address>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/addresses/" + addressId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<Address>>() {
                }
        );
        ResponseStructure<Address> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Address> getAddresses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("API-KEY", apiKey);
        headers.set("USERNAME", username);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ResponseStructure<List<Address>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/addresses",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ResponseStructure<List<Address>>>() {
                }
        );
        ResponseStructure<List<Address>> responseStructure = responseEntities.getBody();
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

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------

}

