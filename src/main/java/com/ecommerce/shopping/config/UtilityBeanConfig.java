package com.ecommerce.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class UtilityBeanConfig {

    @Bean
    Random random(){
        return new SecureRandom();
    }

}
