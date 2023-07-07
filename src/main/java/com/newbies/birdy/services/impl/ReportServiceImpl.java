package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.ReportDTO;
import com.newbies.birdy.dto.WishlistDTO;
import com.newbies.birdy.entities.*;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.ReportMapper;
import com.newbies.birdy.mapper.WishlistMapper;
import com.newbies.birdy.repositories.ReportRepository;
import com.newbies.birdy.repositories.WishlistRepository;
import com.newbies.birdy.services.ReportService;
import com.newbies.birdy.services.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public Boolean addReport(Integer userId, Integer productId, String reason) {
        ReportKey reportKey = new ReportKey(userId, productId);
        //Wishlist wishlist = wishlistRepository.findById(wishlistKey).orElse(null);
        User user = new User();
        Product product = new Product();
        user.setId(userId);
        product.setId(productId);
        return reportRepository.save(new Report(reportKey,reason, new Date(), true, user, product)).getId() != null;
    }

    @Override
    public List<ReportDTO> listReportByUser(Integer userId, Boolean status) {
        List<Report> list = reportRepository.findByUserId(userId, status);
        return list.stream().map(ReportMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<ReportDTO> listReportByProduct(Integer productId, Boolean status) {
        List<Report> list = reportRepository.findByProductId(productId, status);
        return list.stream().map(ReportMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Boolean deleteReport(Integer userId, Integer productId) {
        ReportKey reportKey = new ReportKey(userId, productId);
        Report report = reportRepository
                .findById(reportKey)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));
        reportRepository.delete(report);
        return true;
    }

    @Override
    public ReportDTO getReport(Integer userId, Integer productId) {
        ReportKey reportKey = new ReportKey(userId, productId);
        return ReportMapper.INSTANCE.toDTO(reportRepository
                .findById(reportKey).orElseThrow(() -> new EntityNotFoundException("Report not found")));
    }
}
