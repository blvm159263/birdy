package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.FileImageDTO;
import com.newbies.birdy.dto.ProductImageDTO;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.ProductImage;
import com.newbies.birdy.mapper.ProductImageMapper;
import com.newbies.birdy.repositories.ProductImageRepository;
import com.newbies.birdy.repositories.ProductRepository;
import com.newbies.birdy.services.ProductImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public void saveImages(String[] images, Integer productId) {
        try {
            for (String image : images) {
                ProductImage productImage = new ProductImage();
                productImage.setProductImg(productRepository.findById(productId).orElseThrow());
                productImage.setImgUrl(image);
                productImage.setStatus(true);
                productImageRepository.save(productImage);
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public List<ProductImageDTO> getAllImageByProductId(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<ProductImage> list = productImageRepository.findByProductImgAndStatus(product, true);
        return list.stream().map(ProductImageMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public void deleteImages(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<ProductImage> list = productImageRepository.findByProductImgAndStatus(product, true);
        if (!list.isEmpty()) {
            productImageRepository.deleteAll(list);
        }
    }

    @Override
    public void updateImages(List<FileImageDTO> files, List<Integer> ids, Integer productId) {
        try {
            for (FileImageDTO file : files) {
                ProductImage productImage = productImageRepository.findById(file.getUid()).orElseThrow();
                productImage.setImgUrl(file.getUrl());
                productImageRepository.save(productImage);
            }
            Product product = productRepository.findById(productId).orElseThrow();
            List<ProductImage> list = productImageRepository.findByProductImgAndIdNotIn(product, ids);
            if (!list.isEmpty()) {
                productImageRepository.deleteAll(list);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
