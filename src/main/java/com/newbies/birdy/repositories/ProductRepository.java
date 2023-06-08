package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findTop15ByRatingGreaterThanAndStatusOrderByRatingDesc(Integer rating, Boolean status);

    Page<Product> findByStatus(Boolean status, Pageable page);

    //------------- search and sort (all products) ------------------
    Page<Product> findByProductNameIgnoreCaseContainingAndStatus(String name, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndRatingAndStatus(String name, Integer rating, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndUnitPriceBetweenAndStatus(String name, Double from, Double to, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndRatingAndUnitPriceBetweenAndStatus(String name, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    // --------------------------------------------------------------

    //------------ search and sort in a category ----------------
    Page<Product> findByProductNameContainingIgnoreCaseAndCategoryAndStatus(String search, Category category, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndCategoryAndRatingAndStatus(String name, Category category, Integer rating, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndCategoryAndUnitPriceBetweenAndStatus(String name, Category category, Double from, Double to, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndCategoryAndRatingAndUnitPriceBetweenAndStatus(String name, Category category, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    // ----------------------------------------------------------

    Page<Product> findByCategoryAndStatus(Category category, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndStatus(Shop shop, Boolean status, Pageable pageable);
}
