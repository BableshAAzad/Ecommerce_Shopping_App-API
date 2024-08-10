package com.ecommerce.shopping.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile productImage);

    String getUrlFromPublicId(String publicId);
}
