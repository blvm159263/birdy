package com.newbies.birdy.services;

import com.newbies.birdy.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    Integer updateProduct(ProductDTO productDTO);

    Boolean deleteProduct(Integer productId);

    ProductDTO getProductById(Integer id);

    ProductDTO getProductByIdAndStatus(Integer id, Boolean status);

    List<ProductDTO> getAllProductsByStatus(Boolean status);

    List<ProductDTO> getProductsByNameAndStatus(String name, Boolean status);

    List<ProductDTO> getProductsByCategoryAndStatus(Integer categoryId, Boolean status);

    List<ProductDTO> getProductsByPriceRangeAndStatus(Double from, Double to, Boolean status);

    List<ProductDTO> getProductsByRatingAndStatus(Integer rating, Boolean status);

    List<ProductDTO> getProductsByShopAndStatus(Integer shopId, Boolean status);
}
