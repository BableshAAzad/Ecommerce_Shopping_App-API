package com.ecommerce.shopping.image.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.ecommerce.shopping.enums.ImageConstant;
import com.ecommerce.shopping.image.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile productImage) {
        String filename = UUID.randomUUID().toString();
        try {
            byte[] data = new byte[productImage.getInputStream().available()];
            productImage.getInputStream().read(data);   // set data in array

            Map<String, String> config = new HashMap<String, String>();
            config.put("public_id", filename);

            cloudinary.uploader().upload(data, config);

            return this.getUrlFromPublicId(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation()
                                .width(ImageConstant.PRODUCT_IMAGE_WIDTH)
                                .height(ImageConstant.PRODUCT_IMAGE_HEIGHT)
                                .crop(ImageConstant.PRODUCT_IMAGE_CROP)
                )
                .generate(publicId);
    }
}
