package com.newbies.birdy.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.newbies.birdy.credential.Momo;
import com.newbies.birdy.dto.Response;
import com.newbies.birdy.services.MomoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class MomoController {

    private final MomoService momoService;

    @GetMapping("/momo-info")
    public ResponseEntity<?> momoInfo(
            @RequestParam String partnerCode,
            @RequestParam String orderId,
            @RequestParam String requestId,
            @RequestParam String amount,
            @RequestParam String orderInfo,
            @RequestParam String orderType,
            @RequestParam String transId,
            @RequestParam String resultCode,
            @RequestParam String message,
            @RequestParam String payType,
            @RequestParam String responseTime,
            @RequestParam String extraData,
            @RequestParam String signature
    ) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Response res = momoService.reCheckAndResponseToClient(
                partnerCode,orderId, requestId,
                amount, orderInfo, orderType,
                transId, resultCode, message,
                payType, responseTime, extraData, signature);
        log.info("res: {}", res);
        log.info(Momo.builder()
                .partnerCode(partnerCode)
                .orderId(orderId)
                .requestId(requestId)
                .amount(amount)
                .orderInfo(orderInfo)
                .orderType(orderType)
                .transId(transId)
                .resultCode(resultCode)
                .message(message)
                .payType(payType)
                .responseTime(responseTime)
                .extraData(extraData)
                .signature(signature)
                .build().toString());
        if(res.getStatus().equals("0")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("https://fullstackdeveloper.guru/2021/03/12/how-to-redirect-to-an-external-url-from-spring-boot-rest-controller"))
                    .build();
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam Long amount)
            throws InvalidKeyException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException, IOException {
        Object object = momoService.getPaymentUrl(amount);
        return ResponseEntity.ok(object);
    }
}

