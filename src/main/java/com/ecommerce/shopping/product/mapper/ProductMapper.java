package com.ecommerce.shopping.product.mapper;

import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.warehouse.dto.ProductRequest;
import com.ecommerce.shopping.warehouse.dto.ProductRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapProductRequestToProduct(ProductRequest productRequest, Product product) {
        product.setProductTitle(productRequest.getProductTitle());
        product.setProductDescription(productRequest.getDescription());
        product.setProductPrice(productRequest.getPrice());
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
                .productImage(product.getImages().getFirst().getImage())
                .build();
    }

    public ProductRequestDto mapProductRequestToProductRequestDto(com.ecommerce.shopping.warehouse.dto.ProductRequest productRequest) {
        return ProductRequestDto.builder()
                .sellerId(productRequest.getSellerId())
                .productTitle(productRequest.getProductTitle())
                .lengthInMeters(productRequest.getLengthInMeters())
                .breadthInMeters(productRequest.getBreadthInMeters())
                .heightInMeters(productRequest.getHeightInMeters())
                .weightInKg(productRequest.getWeightInKg())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .materialTypes(productRequest.getMaterialTypes())
                .build();
    }
}
