package com.newbies.birdy.services;

import com.newbies.birdy.dto.ProductDTO;

public interface ProductService {

    Integer updateProduct(ProductDTO productDTO);

    Boolean deleteProduct(Integer productId);
}
