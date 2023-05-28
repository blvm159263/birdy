package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Shop API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shops")
public class ShopController {

    private final ShopService shopService;

    @Operation(summary = "Search shop by given name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found any Shop", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "200", description = "Return Shop List", content = @Content(schema = @Schema(implementation = ShopDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchShopByName(@RequestParam String name){
        List<ShopDTO> list = shopService.listByShopName(name, true);
        if(list == null){
            return new ResponseEntity<>("Not found any shop", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get shop information by shop id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found Shop", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "200", description = "Return Shop", content = @Content(schema = @Schema(implementation = ShopDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    @Operation(summary = "List all shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found any Shop", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "200", description = "Return Shop List", content = @Content(schema = @Schema(implementation = ShopDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("")
    public ResponseEntity<?> listAll(){
        List<ShopDTO> list = shopService.listAllShop(true);
        if(list == null){
            return new ResponseEntity<>("Not found any shop", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(list);
        }
    }
}
