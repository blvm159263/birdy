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

    List<Product> findTop15ByRatingGreaterThanAndStateAndStatusOrderByRatingDesc(Integer rating, Integer state, Boolean status);

    Page<Product> findByStatusAndState(Boolean status, Integer state, Pageable page);

    //------------- search and sort (all products) ------------------
    Page<Product> findByProductNameIgnoreCaseContainingAndStateAndStatus(String name, Integer state, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndRatingAndStatus(String name, Integer state, Integer rating, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndUnitPriceBetweenAndStatus(String name, Integer state, Double from, Double to, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndRatingAndUnitPriceBetweenAndStatus(String name, Integer state, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    // --------------------------------------------------------------

    //------------ search and sort in a category ----------------
    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndStatus(String search, Integer state, Category category, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndStatus(String name, Integer state, Category category, Integer rating, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndUnitPriceBetweenAndStatus(String name, Integer state, Category category, Double from, Double to, Boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndUnitPriceBetweenAndStatus(String name, Integer state, Category category, Integer rating, Double from, Double to, Boolean status, Pageable pageable);

    // ----------------------------------------------------------

    Page<Product> findByCategoryAndStateAndStatus(Category category, Integer state, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndStateAndStatus(Shop shop, Integer state, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndStatus(Shop shop, Boolean status, Pageable pageable);
}
