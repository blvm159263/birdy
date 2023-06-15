package com.newbies.birdy.services;

import com.newbies.birdy.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductDTO getProductById(Integer id);

    List<ProductDTO> getFirst15ProductsWithStatusTrue();

    Map<List<ProductDTO>, Integer> getAllProductsByStatusAndPaging(Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> getProductsByCategoryAndStatusAndPaging(Integer categoryId, Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> getProductsByShopAndStatusAndPaging(Integer shopId, Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> getProductsByShopAndStatusAndPagingForShop(Integer shopId, Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> getProductsByShopInCategoryAndStatusAndPaging(Integer shopId, Integer categoryId, Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> searchAndSortProductsWithPaging(String search, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    Map<List<ProductDTO>, Integer> searchAndSortProductsInCategoryWithPaging(Integer categoryId, String search, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    Integer addProduct(ProductDTO productDTO);
}
