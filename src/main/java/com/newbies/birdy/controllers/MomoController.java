package com.newbies.birdy.controllers;

import com.newbies.birdy.credential.Momo;
import com.newbies.birdy.dto.Response;
import com.newbies.birdy.services.MomoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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
            @RequestParam Optional<String> extraData,
            @RequestParam String signature
    ) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("dang o info");
        Response res = momoService.reCheckAndResponseToClient(
                partnerCode,orderId, requestId,
                amount, orderInfo, orderType,
                transId, resultCode, message,
                payType, responseTime, extraData.orElse(""), signature);
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
                .extraData(extraData.orElse(""))
                .signature(signature)
                .build().toString());
        if(res.getStatus().equals("0")) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://localhost:3000/"))
                    .build();
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/momo")
    public ResponseEntity<?> createOrder(@RequestParam Long amount, @RequestParam String orderId)
            throws InvalidKeyException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException, IOException {
        Object object = momoService.getPaymentUrl(amount, orderId);
        return ResponseEntity.ok(object);
    }
}

