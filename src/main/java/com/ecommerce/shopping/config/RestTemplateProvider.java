package com.ecommerce.shopping.config;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateProvider {

    RestTemplate rest = new RestTemplate();

    public String getStoreHouse() {
        return rest.getForObject("http://localhost:8081/api/v1/test", String.class);
    }
}
