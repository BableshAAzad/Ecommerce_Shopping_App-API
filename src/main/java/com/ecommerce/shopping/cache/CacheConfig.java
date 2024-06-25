package com.ecommerce.shopping.cache;

import com.ecommerce.shopping.user.entity.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig{

    @Bean
    Cache<String, String> otpCache(){
      return CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    @Bean
    Cache<String, User> saveUser(){
        return CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(15))
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }
}
