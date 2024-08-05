package com.ecommerce.shopping.image.repository;

import com.ecommerce.shopping.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
