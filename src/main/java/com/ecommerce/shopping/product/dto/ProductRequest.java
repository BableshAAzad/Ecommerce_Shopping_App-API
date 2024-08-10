package com.ecommerce.shopping.product.dto;

import com.ecommerce.shopping.enums.DiscountType;
import com.ecommerce.shopping.enums.MaterialType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotBlank(message = "Product title is required")
    @Size(max = 100, message = "Product title must not exceed 100 characters")
    private String productTitle;

    @Positive(message = "Length must be a positive number")
    private double lengthInMeters;

    @Positive(message = "Breadth must be a positive number")
    private double breadthInMeters;

    @Positive(message = "Height must be a positive number")
    private double heightInMeters;

    @Positive(message = "Weight must be a positive number")
    private double weightInKg;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

//    @NotNull(message = "Product image is required")
    private MultipartFile productImage;

    @NotEmpty(message = "At least one material type is required")
    private List<MaterialType> materialTypes;

//    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @Max(value = 99, message = "Discount value must not exceed 99%")
    @Min(value = 0, message = "Discount value must be at least 0%")
    private double discount;
}
