package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.OrderDTO;
import com.newbies.birdy.dto.OrderDetailDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final AddressService addressService;

    private final ShopService shopService;

    private final GoogleDistantMatrixService googleDistantMatrixService;

    private final ShipmentService shipmentService;

    @Operation(summary = "Get All Order Detail By Order Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Order Detail not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return List OrderDetailDTO")
    })
    @GetMapping("/order-detail/{order-id}")
    public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable(name = "order-id") Integer orderId) {
        List<OrderDetailDTO> list = orderDetailService.getOrderDetailsByOrderIdAndStatus(orderId, true);
        if (list == null) {
            return new ResponseEntity<>("No order details found", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get All Order By User Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Order or user not found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return List OrderDTO")
    })
    @GetMapping("/user/{user-id}")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable(name = "user-id") Integer userId) {
        List<OrderDTO> list = orderService.getAllOrdersByUserIdAndStatus(userId, true);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get shipment price by shop id, address id and shipment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found!", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "500", description = "Internal error"),
            @ApiResponse(responseCode = "200", description = "Return Shipment price")
    })
    @GetMapping("/shipment-price")
    public ResponseEntity<?> getShippingFee(@RequestParam(name = "shopId") Integer shopId,
                                            @RequestParam(name = "addressId") Integer addressId,
                                            @RequestParam(name = "shipmentId") Integer shipmentId) throws Exception {
        String shopAddress = shopService.getShopAddress(shopId);
        String address = addressService.getAddressById(addressId);
        if (shopAddress == null || address == null) {
            return new ResponseEntity<>("Can not find shop or address", HttpStatus.NOT_FOUND);
        }
        Double price = shipmentService.getShipmentPriceById(shipmentId, true);
        Long distance = googleDistantMatrixService.getData(shopAddress, address);
        Double shipmentPrice = price * (distance / 1000);
        return ResponseEntity.ok(shipmentPrice);
    }
}
