package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Order Detail API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order-details")
public class OrderDetailController {


}