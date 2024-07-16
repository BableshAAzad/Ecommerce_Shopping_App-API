package com.ecommerce.shopping.config;

import com.ecommerce.shopping.product.dto.Product;
import com.ecommerce.shopping.utility.ResponseStructure;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestTemplateProvider {

    RestTemplate restTemplate = new RestTemplate();
//    private Logger logger = LoggerFactory.getLogger(RestTemplateProvider.class);

    public String getTest() {
        return restTemplate.getForObject("http://localhost:8081/api/v1/test", String.class);
    }

    public Product getProduct(Long productId) {
        ResponseEntity<ResponseStructure<Product>> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories/" + productId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<Product>>() {}
        );
        ResponseStructure<Product> responseStructure = responseEntity.getBody();
        return responseStructure.getData();
    }

    public List<Product> getProducts() {
        ResponseEntity<ResponseStructure<List<Product>>> productsResponse = restTemplate.exchange(
                "http://localhost:8081/api/v1/inventories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseStructure<List<Product>>>() {}
        );
        ResponseStructure<List<Product>> responseStructure = productsResponse.getBody();
        return responseStructure.getData();
    }
}
