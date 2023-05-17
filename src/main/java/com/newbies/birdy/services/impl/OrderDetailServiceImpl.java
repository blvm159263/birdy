package com.newbies.birdy.services.impl;

import com.newbies.birdy.services.OrderDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
}
