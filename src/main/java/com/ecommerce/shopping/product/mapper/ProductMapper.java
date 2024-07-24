package com.ecommerce.shopping.product.mapper;

import com.ecommerce.shopping.product.dto.ProductRequest;
import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapProductRequestToProduct(ProductRequest productRequest, Product product) {
        product.setProductId(productRequest.getProductId());
        product.setProductTitle(productRequest.getProductTitle());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductQuantity(productRequest.getProductQuantity());
        product.setAvailabilityStatus(productRequest.getAvailabilityStatus());
        return product;
    }

    public ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .availabilityStatus(product.getAvailabilityStatus())
                .build();
    }
}
