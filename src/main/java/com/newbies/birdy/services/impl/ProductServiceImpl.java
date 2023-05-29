package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.mapper.ProductMapper;
import com.newbies.birdy.repositories.CategoryRepository;
import com.newbies.birdy.repositories.ProductRepository;
import com.newbies.birdy.repositories.ShopRepository;
import com.newbies.birdy.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;

    @Override
    public Integer updateProduct(ProductDTO productDTO) {
        return productRepository.save(ProductMapper.INSTANCE.toEntity(productDTO)).getId();
    }

    @Override
    public Boolean deleteProduct(Integer productId) {
        return null;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        return ProductMapper.INSTANCE.toDTO(productRepository.findById(id).orElseThrow());
    }

    @Override
    public ProductDTO getProductByIdAndStatus(Integer id, Boolean status) {
        return ProductMapper.INSTANCE.toDTO(productRepository.findByIdAndStatus(id, status).orElseThrow());
    }

    @Override
    public List<ProductDTO> getAllProductsByStatus(Boolean status) {
        return productRepository.findByStatus(status).stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsByNameAndStatus(String name, Boolean status) {
        return productRepository.findByProductNameContainingAndStatus(name, status)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndStatus(Integer categoryId, Boolean status) {
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        return productRepository.findByCategoryAndStatus(category, status)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsByPriceRangeAndStatus(Double from, Double to, Boolean status) {
        return productRepository.findByUnitPriceBetweenAndStatus(from, to, status)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsByRatingAndStatus(Integer rating, Boolean status) {
        return productRepository.findByRatingAndStatus(rating, status)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ProductDTO> getProductsByShopAndStatus(Integer shopId, Boolean status) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        return productRepository.findByShopProductAndStatus(shop, status)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }


}
