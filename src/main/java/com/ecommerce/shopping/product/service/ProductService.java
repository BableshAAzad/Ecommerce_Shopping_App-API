package com.ecommerce.shopping.product.service;


import com.ecommerce.shopping.product.dto.ProductResponseDto;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.warehouse.dto.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ResponseEntity<ResponseStructure<ProductResponseDto>> updateProduct(
            Long productId,
            MultipartFile productImage,
            ProductRequest productRequest);

    ResponseEntity<ResponseStructure<ProductResponseDto>> addProduct(
            Long storageId,
            int quantity,
            MultipartFile productImage,
            ProductRequest productRequest);
}
