package com.ecommerce.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173",
                        "https://bableshaazad-e-commerce-shopping-app.vercel.app")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
