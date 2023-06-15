package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.AccountDTO;
import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.dto.ShipmentDTO;
import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.AccountService;
import com.newbies.birdy.services.ProductService;
import com.newbies.birdy.services.ShopService;
import com.newbies.birdy.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Shop API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shops")
public class ShopController {

    private final ShopService shopService;
    private final ProductService productService;
    private final AccountService accountService;
    private final UserService userService;

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

    @Operation(summary = "Get shop detail by shop id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found Shop", content = @Content(schema = @Schema(implementation = ObjectException.class))),
            @ApiResponse(responseCode = "200", description = "Return Shop", content = @Content(schema = @Schema(implementation = ShopDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getShopDetailById(@PathVariable(name = "id") Integer id){
        ShopDTO shop = shopService.getShopById(id);
        AccountDTO account = accountService.getById(shop.getAccountId());
//        UserDTO user = userService.getUserByAccount(AccountMapper.INSTANCE.toEntity(account));
        List<Object> list = new ArrayList<>(Arrays.asList(shop, account));
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "List all active shop")
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

    @Operation(summary = "Search active or inactive shops by name + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/view")
    public ResponseEntity<?> searchShopByNameAndStatusAndPaging(
            @Parameter(description = "Shop status (true=active or false=inactive)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Search shop", example = "Shop") @RequestParam("search") String search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 6);
        Map<List<ShopDTO>, Integer> listMap = shopService.listByNameAndStatusWithPaging(search, status, pageable);
        List<Object> list = new ArrayList<>();
        listMap.forEach((shopDTOS, integer) -> {
            list.add(shopDTOS);
            list.add(integer);
        });
        if (list.isEmpty()) {
            return new ResponseEntity<>("No shop found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products by shop management")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/{status}")
    public ResponseEntity<?> getAllProductsByShopAndStatusForShop(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPagingForShop(id, status, pageable);
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

    @Operation(summary = "Get all available products by shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products")
    public ResponseEntity<?> getAllProductsByShopAndStatus(
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPaging(id, true, pageable);
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

    @Operation(summary = "Get latest products by shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/latest")
    public ResponseEntity<?> getAllLatestProductsByShopAndStatus(
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("id").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPaging(id, true, pageable);
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

    @Operation(summary = "Get all available products in CATEGORY by shop ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/category/{categoryId}")
    public ResponseEntity<?> getAllProductsByShopAndCategoryAndStatus(
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Category ID (1: Bird | 2: Accessories | 3:Food)", example = "1") @PathVariable("categoryId") Integer categoryId,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 20, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopInCategoryAndStatusAndPaging(id, categoryId, true, pageable);
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

    @Operation(summary = "Get all shipment by shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return shipment list", content = @Content(schema = @Schema(implementation = ShipmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/shipment/{shop-id}")
    public ResponseEntity<?> listShipmentByShopId(@PathVariable(name = "shop-id") Integer shopId){
        List<ShipmentDTO> list = shopService.listShipmentByShopId(shopId, true);
        if(list.isEmpty()){
            return new ResponseEntity<>("Not found any shipment", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(list);
        }
    }
}
