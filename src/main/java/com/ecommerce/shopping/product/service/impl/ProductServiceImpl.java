package com.ecommerce.shopping.product.service.impl;

import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.enums.AvailabilityStatus;
import com.ecommerce.shopping.enums.ImageType;
import com.ecommerce.shopping.exception.ImageNotExistException;
import com.ecommerce.shopping.exception.ProductNotExistException;
import com.ecommerce.shopping.image.entity.Image;
import com.ecommerce.shopping.image.repository.ImageRepository;
import com.ecommerce.shopping.image.service.ImageService;
import com.ecommerce.shopping.product.dto.ProductResponseDto;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.product.mapper.ProductMapper;
import com.ecommerce.shopping.product.repository.ProductRepository;
import com.ecommerce.shopping.product.service.ProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.warehouse.dto.ProductRequest;
import com.ecommerce.shopping.warehouse.dto.ProductRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final ProductRepository productRepository;
    private final RestTemplateProvider restTemplateProvider;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<ResponseStructure<ProductResponseDto>> updateProduct(
            Long productId,
            MultipartFile productImage,
            ProductRequest productRequest) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotExistException("Product Id : " + productId + ", is not exist"));

        ProductRequestDto productRequestDto = productMapper.mapProductRequestToProductRequestDto(productRequest);
        if (productImage != null && !productImage.isEmpty()) {
            String imagePath = imageService.uploadImage(productImage);
            Image image = Image.builder()
                    .image(imagePath)
                    .imageType(ImageType.PNG)
                    .product(product)
                    .build();
            imageRepository.save(image);
            productRequestDto.setProductImage(imagePath);
        } else {
            List<Image> images = imageRepository.findByProduct(product);
            if (!images.isEmpty())
                productRequestDto.setProductImage(images.getFirst().getImage());
        }
        productRequestDto.setProductId(productId);
        return restTemplateProvider.updateProduct(productId, productRequestDto);
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<ProductResponseDto>> addProduct(
            Long storageId,
            int quantity,
            MultipartFile productImage,
            ProductRequest productRequest) {

        Product product = productMapper.mapProductRequestToProduct(productRequest, new Product());
        product.setProductQuantity(quantity);
        product.setAvailabilityStatus(AvailabilityStatus.YES);
        product = productRepository.save(product);

        ProductRequestDto productRequestDto = productMapper.mapProductRequestToProductRequestDto(productRequest);
        Image image = null;
        if (productImage != null && !productImage.isEmpty()) {
            String imagePath = imageService.uploadImage(productImage);
            productRequestDto.setProductImage(imagePath);
            image = Image.builder()
                    .image(imagePath)
                    .imageType(ImageType.PNG)
                    .product(product)
                    .build();
            imageRepository.save(image);
        }
        productRequestDto.setProductId(product.getProductId());
        return restTemplateProvider.addProduct(storageId, quantity, productRequestDto);
    }
    //---------------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------------

}
