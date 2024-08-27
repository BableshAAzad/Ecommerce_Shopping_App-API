package com.ecommerce.shopping.product.service;


import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.product.dto.ProductRequest;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ResponseStructure<ProductResponse>> updateProduct(
            Long productId,
            int quantity,
            MultipartFile productImage,
            ProductRequest productRequest);

    ResponseStructure<ProductResponse> addProduct(
            Long storageId,
            int quantity,
            MultipartFile productImage,
            ProductRequest productRequest);

}
