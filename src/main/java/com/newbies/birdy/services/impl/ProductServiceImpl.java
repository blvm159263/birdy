package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.ProductImage;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.ProductMapper;
import com.newbies.birdy.repositories.CategoryRepository;
import com.newbies.birdy.repositories.ProductImageRepository;
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
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;

    private final ProductImageRepository productImageRepository;


    @Override
    public ProductDTO getProductById(Integer id) {
        return ProductMapper.INSTANCE.toDTO(productRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProductDTO> getFirst15ProductsWithStatusTrue() {
        return productRepository.findTop15ByRatingGreaterThanAndStateAndStatusAndQuantityGreaterThanOrderByRatingDesc(3, 1, true, 0)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Map<List<ProductDTO>, Integer> getAllProductsByStatusAndPaging(Boolean status, Integer quantity, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByStatusAndStateAndQuantityGreaterThanOrderByRatingDesc(status, 1, quantity, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByCategoryAndStatusAndPaging(Integer categoryId, Boolean status, Pageable pageable) {
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByCategoryAndStateAndStatus(category, 1, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    /* shop */

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByShopAndStatusAndPaging(Integer shopId, String search, Boolean status, Pageable pageable) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByShopProductAndProductNameContainingIgnoreCaseAndStateAndStatus(shop, search, 1, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }


    // get all shop products for shop management
    @Override
    public Map<List<ProductDTO>, Integer> getProductsByShopAndStatusAndPagingForShop(Integer shopId, String search, Boolean status, Pageable pageable) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByShopProductAndProductNameContainingIgnoreCaseAndStatus(shop, search, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByShopInCategoryAndStatusAndPaging(Integer shopId, String search, Integer categoryId, Boolean status, Pageable pageable) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByShopProductAndProductNameContainingIgnoreCaseAndStateAndCategoryAndStatus(shop, search, 1, category, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Map<List<ProductDTO>, Integer> getProductsByShopInCategoryAndStatusAndPagingForShop(Integer shopId, String search, Integer categoryId, Boolean status, Pageable pageable) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        Category category = categoryRepository.findByIdAndStatus(categoryId, true);
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByShopProductAndProductNameContainingIgnoreCaseAndCategoryAndStatus(shop, search, category, status, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    /* shop */

    @Override
    public Map<List<ProductDTO>, Integer> searchAndSortProductsWithPaging(String search, Integer rating, Double from, Double to, Boolean status, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = null;
        if (rating < 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameIgnoreCaseContainingAndStateAndStatusAndQuantityGreaterThan(search, 1, status, 0, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndRatingAndStatusAndQuantityGreaterThan(search, 1, rating, status, 0, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(search, 1, from, to, status, 0, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndRatingAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(search, 1, rating, from, to, status, 0, pageable);
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
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndStatusAndQuantityGreaterThan(search, 1, category, status, 0, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndStatusAndQuantityGreaterThan(search, 1, category, rating, status, 0, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(search, 1, category, from, to, status, 0, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(search, 1, category, rating, from, to, status, 0, pageable);
        }

        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Integer saveProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        product.setStatus(true);
        return productRepository.save(product).getId();
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Product product = productRepository.findByIdAndStatus(id, true).orElseThrow(() -> new EntityNotFoundException("Product ID doesn't exist"));
        List<ProductImage> images = productImageRepository.findByProductImgAndStatus(product, true);
        for (ProductImage image : images) {
            image.setStatus(false);
            productImageRepository.save(image);
        }
        product.setStatus(false);
        return productRepository.save(product).getStatus();
    }


}
