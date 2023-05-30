package com.newbies.birdy.controllers;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Product API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all available or unavailable products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllProductsByStatus(@Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status) {
        List<ProductDTO> list = productService.getAllProductsByStatus(status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Search available or unavailable products by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/name")
    public ResponseEntity<?> getAllProductsByNameAndStatus(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Search string", example = "bird") @RequestParam("search") String search) {
        List<ProductDTO> list = productService.getProductsByNameAndStatus(search, status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/category/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategoryAndStatus(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Category ID (1=bird, 2=accessories, 3=food", example = "1") @PathVariable("categoryId") Integer categoryId) {
        List<ProductDTO> list = productService.getProductsByCategoryAndStatus(categoryId, status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products by price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/price")
    public ResponseEntity<?> getAllProductsByPriceRangeAndStatus(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Price rage start", example = "10") @RequestParam("from") Double from,
            @Parameter(description = "Price rage end", example = "50") @RequestParam("to") Double to) {
        List<ProductDTO> list = productService.getProductsByPriceRangeAndStatus(from, to, status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products by rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/rating/{rating}")
    public ResponseEntity<?> getAllProductsByRatingAndStatus(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Rating", example = "4") @PathVariable("rating") Integer rating) {
        List<ProductDTO> list = productService.getProductsByRatingAndStatus(rating, status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products by shop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{status}/shop/{shopId}")
    public ResponseEntity<?> getAllProductsByShopAndStatus(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Shop ID", example = "1") @PathVariable("shopId") Integer shopId) {
        List<ProductDTO> list = productService.getProductsByShopAndStatus(shopId, status);
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get details of available products by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/true/{id}")
    public ResponseEntity<?> getProductDetailByIdAndAvailable(@Parameter(description = "Product ID", example = "1") @PathVariable("id") Integer id) {
        try {
            ProductDTO product = productService.getProductByIdAndStatus(id, true);
            return ResponseEntity.ok(product);
        } catch (Exception e){
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get details of available or unavailable products by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetailById(@Parameter(description = "Product ID", example = "1") @PathVariable("id") Integer id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e){
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        }
    }
}
