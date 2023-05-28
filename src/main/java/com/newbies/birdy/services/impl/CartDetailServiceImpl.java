package com.newbies.birdy.services.impl;

import com.newbies.birdy.services.CartDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
}
