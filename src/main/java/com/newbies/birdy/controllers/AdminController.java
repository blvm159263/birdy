package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.services.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(name = "search") Optional<String> search,
            @RequestParam(name = "page") Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by("id").ascending());
        Map<List<ProductDTO>, Integer> listMap = adminService.getAllProductsForAdmin(search.orElse(""), pageable);
        List<Object> list = new ArrayList<>();
        listMap.forEach((productDTOS, integer) -> {
            list.add(productDTOS);
            list.add(integer);
        });
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/product/{product-id}/approve")
    public ResponseEntity<?> approveProduct(@PathVariable("product-id") Integer productId) {
        Integer id = adminService.approveProduct(productId);
        if (id == null) {
            return new ResponseEntity<>("Failed!!!", HttpStatus.CONFLICT);
        }
        else return new ResponseEntity<>("Success!!!", HttpStatus.OK);
    }

    @GetMapping("/product/{product-id}/decline")
    public ResponseEntity<?> declineProduct(@PathVariable("product-id") Integer productId) {
        Boolean del = adminService.declineProduct(productId);
        if (Boolean.FALSE.equals(del)) {
            return new ResponseEntity<>("Failed!!!", HttpStatus.CONFLICT);
        }
        else return new ResponseEntity<>("Success!!!", HttpStatus.OK);
    }
}
