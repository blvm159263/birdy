package com.newbies.birdy.repositories;

import com.newbies.birdy.entities.Report;
import com.newbies.birdy.entities.ReportKey;
import com.newbies.birdy.entities.Wishlist;
import com.newbies.birdy.entities.WishlistKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, ReportKey> {

    @Query("SELECT w FROM Report w WHERE w.id.userId = ?1 AND w.status = ?2")
    List<Report> findByUserId(Integer userId, Boolean status);

    @Query("SELECT w FROM Report w WHERE w.id.productId = ?1 AND w.status = ?2")
    List<Report> findByProductId(Integer productId, Boolean status);

}
