package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByIdAndStatus(Integer id, Boolean status);

    Optional<Product> findById(Integer id);

    List<Product> findByStatus(Boolean status);

    List<Product> findByProductNameContainingAndStatus(String name, Boolean status);

    List<Product> findByCategoryAndStatus(Category category, Boolean status);

    List<Product> findByUnitPriceBetweenAndStatus(Double from, Double to, Boolean status);

    List<Product> findByRatingAndStatus(Integer rating, Boolean status);

    List<Product> findByShopProductAndStatus(Shop shop, Boolean status);
}
