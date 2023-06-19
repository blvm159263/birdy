package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.*;
import com.newbies.birdy.exceptions.ObjectException;
import com.newbies.birdy.services.*;
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
    private final OrderService orderService;
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

    @Operation(summary = "Get all products by shop for shop management")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/management")
    public ResponseEntity<?> getAllProductsByShopAndStatusForShop(
//            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPagingForShop(id,search.orElse(""), true, pageable);
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

    @Operation(summary = "Get all products by shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products")
    public ResponseEntity<?> getAllProductsByShopAndStatus(
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Search Products", example = "a") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPaging(id, search.orElse(""),true, pageable);
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
            @Parameter(description = "Search Products", example = "a") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("id").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPaging(id, search.orElse(""),true, pageable);
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

    @Operation(summary = "Get latest products by shop for shop management")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/latest/management")
    public ResponseEntity<?> getAllLatestProductsByShopAndStatusForShop(
//            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by("id").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopAndStatusAndPagingForShop(id,search.orElse(""), true, pageable);
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

    @Operation(summary = "Get all products in CATEGORY by shop ")
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
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopInCategoryAndStatusAndPaging(id, search.orElse(""), categoryId, true, pageable);
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

    @Operation(summary = "Get all products in CATEGORY by shop for shop management")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}/products/category/{categoryId}/management")
    public ResponseEntity<?> getAllProductsByShopAndCategoryAndStatusForShop(
            @Parameter(description = "Shop ID", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Category ID (1: Bird | 2: Accessories | 3:Food)", example = "1") @PathVariable("categoryId") Integer categoryId,
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getProductsByShopInCategoryAndStatusAndPagingForShop(id, search.orElse(""), categoryId, true, pageable);
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


    @Operation(summary = "Gets all order by shop + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return orders list", content = @Content(schema = @Schema(implementation = OrderManageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{shop-id}/orders")
    public ResponseEntity<?> listOrdersByShopId(
            @Parameter(example = "1", description = "Shop Id") @PathVariable(name = "shop-id") Integer shopId,
            @Parameter(example = "asc", description = "Sort asc or desc by id (desc = get latest orders first)") @RequestParam("sort") String sort,
            @Parameter(description = "Search by customer name") @RequestParam("search") Optional<String> search,
            @Parameter(example = "done", description = "Order state") @RequestParam("status") Optional<String> state,
            @Parameter(example = "paid", description = "Payment status") @RequestParam("payment") Optional<String> payment,
            @Parameter(example = "0", description = "Page number (start from 0)") @RequestParam("page") Optional<Integer> page){

        Pageable pageable;
        if (sort.equals("desc")) {
            pageable = PageRequest.of(page.orElse(0), 3, Sort.by("id").descending());
        } else {
            pageable = PageRequest.of(page.orElse(0), 3, Sort.by("id").ascending());
        }

        Map<List<OrderManageDTO>, Integer> listMap = orderService.getAllOrderByShop(shopId, search.orElse(""), state.orElse(""), payment.orElse(""), pageable);

        List<Object> list = new ArrayList<>();
        listMap.forEach((orderManageDTOS, integer) -> {
            list.add(orderManageDTOS);
            list.add(integer);
        });
        if(list.isEmpty()){
            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(list);
        }
    }


//    @Operation(summary = "Gets all order by shop + paging")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Return orders list", content = @Content(schema = @Schema(implementation = OrderManageDTO.class))),
//            @ApiResponse(responseCode = "404", description = "Not found"),
//            @ApiResponse(responseCode = "400", description = "Bad request"),
//            @ApiResponse(responseCode = "500", description = "Internal error")
//    })
//    @GetMapping("/{shop-id}/orders")
//    public ResponseEntity<?> listOrdersByShopId(
//            @Parameter(example = "1", description = "Shop Id") @PathVariable(name = "shop-id") Integer shopId,
//            @Parameter(example = "0", description = "Page number (start from 0)") @RequestParam("page") Optional<Integer> page){
//        Pageable pageable = PageRequest.of(page.orElse(0), 3, Sort.by("id").descending());
//        Map<List<OrderManageDTO>, Integer> listMap = orderService.getAllOrdersByShopId(shopId, pageable);
//
//        List<Object> list = new ArrayList<>();
//        listMap.forEach((orderManageDTOS, integer) -> {
//            list.add(orderManageDTOS);
//            list.add(integer);
//        });
//        if(list.isEmpty()){
//            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
//        }else{
//            return ResponseEntity.ok(list);
//        }
//    }

//    @Operation(summary = "Gets all order by shop by STATE + paging")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Return orders list", content = @Content(schema = @Schema(implementation = OrderManageDTO.class))),
//            @ApiResponse(responseCode = "404", description = "Not found"),
//            @ApiResponse(responseCode = "400", description = "Bad request"),
//            @ApiResponse(responseCode = "500", description = "Internal error")
//    })
//    @GetMapping("/{shop-id}/orders/state/{state}")
//    public ResponseEntity<?> listOrdersByShopIdAndState(
//            @Parameter(example = "1", description = "Shop Id") @PathVariable(name = "shop-id") Integer shopId,
//            @Parameter(example = "pending", description = "Order state") @PathVariable(name = "state") String state,
//            @Parameter(example = "0", description = "Page number (start from 0)") @RequestParam("page") Optional<Integer> page){
//        Pageable pageable = PageRequest.of(page.orElse(0), 3, Sort.by("id").descending());
//        Map<List<OrderManageDTO>, Integer> listMap = orderService.getAllOrdersByShopIdAndState(shopId, state, pageable);
//
//        List<Object> list = new ArrayList<>();
//        listMap.forEach((orderManageDTOS, integer) -> {
//            list.add(orderManageDTOS);
//            list.add(integer);
//        });
//        if(list.isEmpty()){
//            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
//        }else{
//            return ResponseEntity.ok(list);
//        }
//    }

}
