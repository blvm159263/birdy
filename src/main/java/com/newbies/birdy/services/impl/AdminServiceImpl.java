package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.mapper.ProductMapper;
import com.newbies.birdy.repositories.ProductRepository;
import com.newbies.birdy.services.AdminService;
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
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;

    @Override
    public Map<List<ProductDTO>, Integer> getAllProductsForAdmin(String search, Pageable pageable) {
        Map<List<ProductDTO>, Integer> pair = new HashMap<>();
        Page<Product> pageList = productRepository.findByProductNameContainingIgnoreCaseAndStateAndStatus(search, 0, true, pageable);
        pair.put(pageList.stream().map(ProductMapper.INSTANCE::toDTO).toList(), pageList.getTotalPages());
        return pair;
    }

    @Override
    public Integer approveProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setState(1);
        return productRepository.save(product).getId();
    }

    @Override
    public Boolean declineProduct(Integer id) {
        try {
            Product product = productRepository.findById(id).orElseThrow();
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
