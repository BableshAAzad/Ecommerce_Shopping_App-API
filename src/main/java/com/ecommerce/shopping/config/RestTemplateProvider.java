package com.ecommerce.shopping.config;

import com.ecommerce.shopping.warehouse.dto.*;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class RestTemplateProvider {

    RestTemplate restTemplate = new RestTemplate();
//    private Logger logger = LoggerFactory.getLogger(RestTemplateProvider.class);

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
    public WareHouse getWareHouse(Long wareHouseId) {
        ResponseEntity<ResponseStructure<WareHouse>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storehouses/" + wareHouseId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<WareHouse>>() {
                }
        );
        ResponseStructure<WareHouse> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<WareHouse> getWareHouses() {
        ResponseEntity<ResponseStructure<List<WareHouse>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storehouses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<WareHouse>>>() {
                }
        );
        ResponseStructure<List<WareHouse>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public StorageType getStorageType(Long storageTypeId) {
        ResponseEntity<ResponseStructure<StorageType>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storageTypes/" + storageTypeId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<StorageType>>() {
                }
        );
        ResponseStructure<StorageType> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<StorageType> getStorageTypes() {
        ResponseEntity<ResponseStructure<List<StorageType>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storageTypes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<StorageType>>>() {
                }
        );
        ResponseStructure<List<StorageType>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public Storage getStorage(Long storageId) {
        ResponseEntity<ResponseStructure<Storage>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/storages/" + storageId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<Storage>>() {
                }
        );
        ResponseStructure<Storage> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Storage> getStorages() {
        ResponseEntity<ResponseStructure<List<Storage>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/storages",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<Storage>>>() {
                }
        );
        ResponseStructure<List<Storage>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public Address getAddress(Long addressId) {
        ResponseEntity<ResponseStructure<Address>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/addresses/" + addressId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<Address>>() {
                }
        );
        ResponseStructure<Address> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Address> getAddresses() {
        ResponseEntity<ResponseStructure<List<Address>>> responseEntities = restTemplate.exchange(
                "http://localhost:8081/api/v1/addresses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<Address>>>() {
                }
        );
        ResponseStructure<List<Address>> responseStructure = responseEntities.getBody();
        return responseStructure.getData();
    }

    //---------------------------------------------------------------------------------------------------
    public List<Map<String, Object>> getWareHousesByCity(String city) {
        ResponseEntity<ResponseStructure<List<Map<String, Object>>>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/client/cities/" + city + "/storehouses",
                HttpMethod.GET,
                null,
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

