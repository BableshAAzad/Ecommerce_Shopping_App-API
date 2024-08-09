package com.ecommerce.shopping.product.service.impl;

import com.ecommerce.shopping.cartproduct.entity.CartProduct;
import com.ecommerce.shopping.cartproduct.repository.CartProductRepository;
import com.ecommerce.shopping.config.RestTemplateProvider;
import com.ecommerce.shopping.discount.entity.Discount;
import com.ecommerce.shopping.discount.repository.DiscountRepository;
import com.ecommerce.shopping.enums.AvailabilityStatus;
import com.ecommerce.shopping.enums.DiscountType;
import com.ecommerce.shopping.enums.ImageType;
import com.ecommerce.shopping.exception.FileSizeExceededException;
import com.ecommerce.shopping.exception.InvalidFileFormatException;
import com.ecommerce.shopping.exception.ProductNotExistException;
import com.ecommerce.shopping.image.entity.Image;
import com.ecommerce.shopping.image.repository.ImageRepository;
import com.ecommerce.shopping.image.service.ImageService;
import com.ecommerce.shopping.product.dto.ProductResponse;
import com.ecommerce.shopping.product.entity.Product;
import com.ecommerce.shopping.product.mapper.ProductMapper;
import com.ecommerce.shopping.product.repository.ProductRepository;
import com.ecommerce.shopping.product.service.ProductService;
import com.ecommerce.shopping.utility.ResponseStructure;
import com.ecommerce.shopping.product.dto.ProductRequest;
import com.ecommerce.shopping.product.dto.ProductRequestDto;
import com.ecommerce.shopping.warehouse.dto.Inventory;
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
    private final DiscountRepository discountRepository;
    private final CartProductRepository cartProductRepository;

    @Override
    public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(
            Long productId,
            MultipartFile productImage,
            ProductRequest productRequest) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotExistException("Product Id : " + productId + ", is not exist"));

        ProductRequestDto productRequestDto = productMapper.mapProductRequestToProductRequestDto(productRequest);
        if (productImage != null && !productImage.isEmpty()) {
            // Validate content type
            String contentType = productImage.getContentType();
            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                            contentType.equals("image/png") ||
                            contentType.equals("image/jpg"))) {
                throw new InvalidFileFormatException("Only JPEG, PNG, and JPG formats are supported.");
            }
            // Validate file size
            long maxSizeInBytes = 2 * 1024 * 1024; // 2 MB
            if (productImage.getSize() > maxSizeInBytes) {
                throw new FileSizeExceededException("File size must be less than 2 MB.");
            }
            String imagePath = imageService.uploadImage(productImage);
            // Determine the image type dynamically
            ImageType imageType = determineImageType(contentType);
            Image image = Image.builder()
                    .image(imagePath)
                    .imageType(imageType)
                    .product(product)
                    .build();
            imageRepository.save(image);
            productRequestDto.setProductImage(imagePath);
        } else {
            List<Image> images = imageRepository.findByProduct(product);
            if (!images.isEmpty())
                productRequestDto.setProductImage(images.getFirst().getImage());
        }
        //   update discount
        updateDiscount(productRequest, product);
        //   Update cart-product
        updateCartProduct(product);

        productRequestDto.setProductId(productId);
        return restTemplateProvider.updateProduct(productId, productRequestDto);
    }

    //---------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(
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
            // Validate content type
            String contentType = productImage.getContentType();
            if (contentType == null ||
                    !(contentType.equals("image/jpeg") ||
                            contentType.equals("image/png") ||
                            contentType.equals("image/jpg"))) {
                throw new InvalidFileFormatException("Only JPEG, PNG, and JPG formats are supported.");
            }
            // Validate file size
            long maxSizeInBytes = 2 * 1024 * 1024; // 2 MB
            if (productImage.getSize() > maxSizeInBytes) {
                throw new FileSizeExceededException("File size must be less than 2 MB.");
            }
            String imagePath = imageService.uploadImage(productImage);
            productRequestDto.setProductImage(imagePath);
            // Determine the image type dynamically
            ImageType imageType = determineImageType(contentType);
            image = Image.builder()
                    .image(imagePath)
                    .imageType(imageType)
                    .product(product)
                    .build();
            imageRepository.save(image);
        }
        //  save Discount object
        saveDiscount(productRequest, product);

        productRequestDto.setProductId(product.getProductId());
        return restTemplateProvider.addProduct(storageId, quantity, productRequestDto);
    }

    //---------------------------------------------------------------------------------------------------
//    save thee Discount object
    private void saveDiscount(ProductRequest productRequest, Product product) {
        Discount discount = new Discount();
        discount.setProduct(product);

        if (productRequest.getDiscountType() != null) {
            discount.setDiscountType(productRequest.getDiscountType());
        } else {
            discount.setDiscountType(DiscountType.FLAT);
        }
        if (productRequest.getDiscount() != 0.0) {
            discount.setDiscountValue(productRequest.getDiscount());
        } else {
            discount.setDiscountValue(0.0);
        }
        discountRepository.save(discount);
    }

    //---------------------------------------------------------------------------------------------------
// Helper method to determine ImageType based on content type
    private ImageType determineImageType(String contentType) {
        return switch (contentType.toLowerCase()) {
            case "image/jpeg" -> ImageType.JPEG;
            case "image/png" -> ImageType.PNG;
            case "image/jpg" -> ImageType.JPG;
            default -> throw new InvalidFileFormatException("Unsupported image format.");
        };
    }

    //---------------------------------------------------------------------------------------------------
//    Update discount
    private void updateDiscount(ProductRequest productRequest, Product product) {
        List<Discount> discounts = discountRepository.findByProductAndIsActiveTrue(product);
        Discount discount = discounts.getFirst();
        discount.setDiscountValue(productRequest.getDiscount());
        discount.setDiscountType(productRequest.getDiscountType());
        discountRepository.save(discount);
    }

    //---------------------------------------------------------------------------------------------------
// if update product then also update all cart-products
    private void updateCartProduct(Product product) {
        cartProductRepository.findByProduct(product).forEach(cartProduct -> {
            cartProduct.setProduct(product);
            cartProductRepository.save(cartProduct);
        });
    }
    //---------------------------------------------------------------------------------------------------

}
