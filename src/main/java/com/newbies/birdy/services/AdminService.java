package com.newbies.birdy.services;

import com.newbies.birdy.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Map<List<ProductDTO>, Integer> getAllProductsForAdmin(String search, Pageable pageable);

    Integer approveProduct(Integer id);
}
