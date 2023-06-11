package com.newbies.birdy.services;

import com.newbies.birdy.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public interface MomoService {

    Response reCheckAndResponseToClient(
            String partnerCode, String orderId, String requestId,
            String amount, String orderInfo, String orderType,
            String transId, String resultCode, String message,
            String payType, String responseTime, String extraData,
            String signature
    ) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException;

     Object getPaymentUrl(Long amount)
            throws InvalidKeyException,
            NoSuchAlgorithmException,
            IOException, UnsupportedEncodingException;
}
