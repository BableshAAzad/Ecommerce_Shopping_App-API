package com.ecommerce.shopping.product.mapper;

import com.ecommerce.shopping.discount.entity.Discount;
import com.ecommerce.shopping.discount.repository.DiscountRepository;
import com.ecommerce.shopping.image.entity.Image;
import com.ecommerce.shopping.image.repository.ImageRepository;
import com.ecommerce.shopping.product.dto.ProductResponseCart;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.product.dto.ProductRequest;
import com.ecommerce.shopping.product.dto.ProductRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductMapper {

    private final ImageRepository imageRepository;
    private final DiscountRepository discountRepository;

    public Product mapProductRequestToProduct(ProductRequest productRequest, Product product) {
        product.setProductTitle(productRequest.getProductTitle());
        product.setProductDescription(productRequest.getDescription());
        product.setProductPrice(productRequest.getPrice());
        return product;
    }

    public ProductResponseCart mapProductToProductResponseCart(Product product) {
        List<Image> images = imageRepository.findByProduct(product);
        List<Discount> discounts = discountRepository.findByProductAndIsActiveTrue(product);
        return ProductResponseCart.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .availabilityStatus(product.getAvailabilityStatus())
                .productImage(images.getFirst().getImage())
                .discount(discounts.getFirst().getDiscountValue())
                .build();
    }

    public ProductRequestDto mapProductRequestToProductRequestDto(ProductRequest productRequest) {
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
                .discount(productRequest.getDiscount())
                .discountType(productRequest.getDiscountType())
                .build();
    }
}
