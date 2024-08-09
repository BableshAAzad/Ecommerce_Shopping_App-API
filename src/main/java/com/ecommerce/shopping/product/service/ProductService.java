package com.ecommerce.shopping.product.service;


import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.product.dto.ProductRequest;
import com.ecommerce.shopping.warehouse.dto.Inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(
            Long productId,
            MultipartFile productImage,
            ProductRequest productRequest);

    ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
            Long storageId,
            int quantity,
            MultipartFile productImage,
            ProductRequest productRequest);

}
