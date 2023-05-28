package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shops")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/search")
    public ResponseEntity<?> searchShopByName(@RequestParam String name){
        List<ShopDTO> list = shopService.listByShopName(name, true);
        if(list == null){
            return new ResponseEntity<>("Not found any shop", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(shopService.getShopById(id));
    }
}
