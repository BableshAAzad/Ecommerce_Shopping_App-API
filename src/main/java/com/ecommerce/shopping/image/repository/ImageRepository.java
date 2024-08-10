package com.ecommerce.shopping.image.repository;

import com.ecommerce.shopping.image.entity.Image;
import com.ecommerce.shopping.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProduct(Product product);
}
