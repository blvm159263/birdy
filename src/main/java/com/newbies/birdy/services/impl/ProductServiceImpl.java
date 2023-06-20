package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
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
        return productRepository.findTop15ByRatingGreaterThanAndStateAndStatusOrderByRatingDesc(3, 1, true)
                .stream().map(ProductMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Map<List<ProductDTO>, Integer> getAllProductsByStatusAndPaging(Boolean status, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByStatusAndStateOrderByRatingDesc(status, 1, pageable);
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
            pageList = productRepository.findByProductNameIgnoreCaseContainingAndStateAndStatus(search, 1, status, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndRatingAndStatus(search, 1, rating, status, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndUnitPriceBetweenAndStatus(search, 1, from, to, status, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndRatingAndUnitPriceBetweenAndStatus(search, 1, rating, from, to, status, pageable);
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
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndStatus(search, 1, category, status, pageable);
        } else if(rating >= 0 && from < 0 && to < 0){
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndStatus(search, 1, category, rating, status, pageable);
        } else if (rating < 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndUnitPriceBetweenAndStatus(search, 1, category, from, to, status, pageable);
        } else if (rating >= 0 && from >= 0 && to >= from) {
            pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndUnitPriceBetweenAndStatus(search, 1, category, rating, from, to, status, pageable);
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
        product.setStatus(false);
        return productRepository.save(product).getStatus();
    }


}
