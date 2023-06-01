package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.mapper.ProductMapper;
import com.newbies.birdy.repositories.CategoryRepository;
import com.newbies.birdy.repositories.ProductRepository;
import com.newbies.birdy.repositories.ShopRepository;
import com.newbies.birdy.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;


    @Override
    public ProductDTO getProductById(Integer id) {
        return ProductMapper.INSTANCE.toDTO(productRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDTO> getFirst15ProductsWithStatusTrue() {
        return productRepository.findTop15ByRatingGreaterThanAndStatusOrderByRatingDesc(3, true)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Map<List<ProductDTO>, Integer> getAllProductsByStatusAndPaging(Boolean status, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByStatus(status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByCategoryAndStatusAndPaging(Integer categoryId, Boolean status, Pageable pageable) {
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByCategoryAndStatus(category, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByShopAndStatusAndPaging(Integer shopId, Boolean status, Pageable pageable) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByShopProductAndStatus(shop, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> searchAndSortProductsWithPaging(String search, Integer rating, Double from, Double to, Boolean status, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = null;
        if (rating < 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameIgnoreCaseContainingAndStatus(search, status, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndRatingAndStatus(search, rating, status, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndUnitPriceBetweenAndStatus(search, from, to, status, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndRatingAndUnitPriceBetweenAndStatus(search, rating, from, to, status, pageable);
        }

        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> searchAndSortProductsInCategoryWithPaging(Integer categoryId, String search, Integer rating, Double from, Double to, Boolean status, Pageable pageable) {
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = null;
        if (rating < 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndCategoryAndStatus(search, category, status, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndCategoryAndRatingAndStatus(search, category, rating, status, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndCategoryAndUnitPriceBetweenAndStatus(search, category, from, to, status, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndCategoryAndRatingAndUnitPriceBetweenAndStatus(search, category, rating, from, to, status, pageable);
        }

        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }


}
