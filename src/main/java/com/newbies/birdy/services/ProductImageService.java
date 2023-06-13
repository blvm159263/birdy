package com.newbies.birdy.services;

import com.newbies.birdy.dto.ProductImageDTO;

import java.util.List;

public interface ProductImageService {

    void saveImages(String[] images, Integer productId);

    List<ProductImageDTO> getAllImageByProductId(Integer productId);
}
