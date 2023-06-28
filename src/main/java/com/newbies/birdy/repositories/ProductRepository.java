package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findTop15ByRatingGreaterThanAndStateAndStatusAndQuantityGreaterThanOrderByRatingDesc(Integer rating, Integer state, Boolean status, Integer quantity);

    Page<Product> findByStatusAndStateAndQuantityGreaterThanOrderByRatingDesc(Boolean status, Integer state, Integer quantity, Pageable page);

    //------------- search and sort (all products) ------------------
    Page<Product> findByProductNameIgnoreCaseContainingAndStateAndStatusAndQuantityGreaterThan(String name, Integer state, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndRatingAndStatusAndQuantityGreaterThan(String name, Integer state, Integer rating, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(String name, Integer state, Double from, Double to, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndRatingAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(String name, Integer state, Integer rating, Double from, Double to, Boolean status, Integer quantity, Pageable pageable);

    // --------------------------------------------------------------

    //------------ search and sort in a category ----------------
    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndStatusAndQuantityGreaterThan(String search, Integer state, Category category, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndStatusAndQuantityGreaterThan(String name, Integer state, Category category, Integer rating, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(String name, Integer state, Category category, Double from, Double to, Boolean status, Integer quantity, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndStateAndCategoryAndRatingAndUnitPriceBetweenAndStatusAndQuantityGreaterThan(String name, Integer state, Category category, Integer rating, Double from, Double to, Boolean status, Integer quantity, Pageable pageable);

    // ----------------------------------------------------------

    Page<Product> findByCategoryAndStateAndStatus(Category category, Integer state, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndProductNameContainingIgnoreCaseAndStateAndStatus(Shop shop, String search, Integer state, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndProductNameContainingIgnoreCaseAndStatus(Shop shop, String search, Boolean status, Pageable pageable);

    Optional<Product> findByIdAndStatus(Integer id, Boolean status);

    Page<Product> findByShopProductAndProductNameContainingIgnoreCaseAndStateAndCategoryAndStatus(Shop shop, String search, Integer state, Category category, Boolean status, Pageable pageable);

    Page<Product> findByShopProductAndProductNameContainingIgnoreCaseAndCategoryAndStatus(Shop shop, String search, Category category, Boolean status, Pageable pageable);
}
