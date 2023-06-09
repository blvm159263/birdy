package com.newbies.birdy.services.impl;

import com.newbies.birdy.entities.ProductImage;
import com.newbies.birdy.repositories.ProductImageRepository;
import com.newbies.birdy.repositories.ProductRepository;
import com.newbies.birdy.services.ProductImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public void saveImages(String[] images, Integer productId) {
        try{
            for (String image : images) {
                ProductImage productImage = new ProductImage();
                productImage.setProductImg(productRepository.findById(productId).orElseThrow());
                productImage.setImgUrl(image);
                productImageRepository.save(productImage);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
