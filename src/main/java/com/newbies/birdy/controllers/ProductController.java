package com.newbies.birdy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.dto.ReviewDTO;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.services.FirebaseStorageService;
import com.newbies.birdy.services.ProductImageService;
import com.newbies.birdy.services.ProductService;
import com.newbies.birdy.services.ReviewService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Product API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    private final ProductImageService productImageService;

    private final FirebaseStorageService firebaseStorageService;

    private final ReviewService reviewService;

    @Operation(summary = "Get first 15 available products for landing page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/landing-page")
    public ResponseEntity<?> getFirst15Products() {
        List<ProductDTO> list = productService.getFirst15ProductsWithStatusTrue();
        if (list.isEmpty()) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(summary = "Get all available or unavailable products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllProductsByStatusAndPaging(
            @Parameter(description = "Products status (true=available or false=unavailable)", example = "true") @PathVariable("status") Boolean status,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30);
        Map<List<ProductDTO>, Integer> listMap = productService.getAllProductsByStatusAndPaging(status, pageable);
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

    @Operation(summary = "View all products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/view/all")
    public ResponseEntity<?> getAllProductsAndPaging(
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.getAllProductsByStatusAndPaging(true, pageable);
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

    @Operation(summary = "Get product details by ID")
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
        } catch (Exception e) {
            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Search relevant available products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/view")
    public ResponseEntity<?> searchRelevantProductsAndPaging(
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Products Rating", example = "4") @RequestParam("rating") Optional<Integer> rating,
            @Parameter(description = "Price range start", example = "10.45") @RequestParam("from") Optional<Double> from,
            @Parameter(description = "Price range end", example = "30.65") @RequestParam("to") Optional<Double> to,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by("rating").descending());
        Map<List<ProductDTO>, Integer> listMap = productService.searchAndSortProductsWithPaging(search.orElse(""),
                rating.orElse(-1), from.orElse((double) -1), to.orElse((double) -1), true, pageable);
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

    @Operation(summary = "Search latest available products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/view/latest")
    public ResponseEntity<?> searchLatestProductsStatusTrueAndPaging(
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Products Rating", example = "4") @RequestParam("rating") Optional<Integer> rating,
            @Parameter(description = "Price range start", example = "10.88") @RequestParam("from") Optional<Double> from,
            @Parameter(description = "Price range end", example = "30.99") @RequestParam("to") Optional<Double> to,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by(Sort.Direction.DESC, "id"));
        Map<List<ProductDTO>, Integer> listMap = productService.searchAndSortProductsWithPaging(search.orElse(""),
                rating.orElse(-1), from.orElse((double) -1), to.orElse((double) -1), true, pageable);
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

    @Operation(summary = "Search and sort price ascending available products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/view/price-asc")
    public ResponseEntity<?> searchAndSortPriceAscProductsStatusTrueAndPaging(
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Products Rating", example = "4") @RequestParam("rating") Optional<Integer> rating,
            @Parameter(description = "Price range start", example = "10.23") @RequestParam("from") Optional<Double> from,
            @Parameter(description = "Price range end", example = "30.9") @RequestParam("to") Optional<Double> to,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by(Sort.Direction.ASC, "unitPrice"));
        Map<List<ProductDTO>, Integer> listMap = productService.searchAndSortProductsWithPaging(search.orElse(""),
                rating.orElse(-1), from.orElse((double) -1), to.orElse((double) -1), true, pageable);
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

    @Operation(summary = "Search and sort price descending available products + paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Products List", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/view/price-desc")
    public ResponseEntity<?> searchAndSortPriceDescProductsStatusTrueAndPaging(
            @Parameter(description = "Search Products", example = "bird") @RequestParam("search") Optional<String> search,
            @Parameter(description = "Products Rating", example = "4") @RequestParam("rating") Optional<Integer> rating,
            @Parameter(description = "Price range start", example = "10.12") @RequestParam("from") Optional<Double> from,
            @Parameter(description = "Price range end", example = "30.9") @RequestParam("to") Optional<Double> to,
            @Parameter(description = "Page number (start from 0)", example = "0") @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 30, Sort.by(Sort.Direction.DESC, "unitPrice"));
        Map<List<ProductDTO>, Integer> listMap = productService.searchAndSortProductsWithPaging(search.orElse(""),
                rating.orElse(-1), from.orElse((double) -1), to.orElse((double) -1), true, pageable);
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

//    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> test(@RequestBody MultipartFile mainImage) {
//        if (mainImage != null) {
//            return ResponseEntity.ok(mainImage.getOriginalFilename());
//        } else {
//            return new ResponseEntity<>("No product found!!!", HttpStatus.NOT_FOUND);
//        }
////        MultipartFile mainImage = request;
////        MultipartFile[] subImages = productRequestDTO.getSubImages();
////        List<Object> list = new ArrayList<>(Arrays.asList(mainImage, subImages));return ResponseEntity.ok(request);
//    }

//    @PostMapping(value = "/test", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> test(
//            @RequestPart(value = "productDTO") String jsonString,
//            @RequestPart(value = "mainImage") MultipartFile mainImage,
//            @RequestPart(value = "subImages", required = false) MultipartFile[] subImages
//    ) {
//        try {
//            if (mainImage != null && jsonString != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                ProductDTO productDTO = objectMapper.readValue(jsonString, ProductDTO.class);
//
//                List<String> subImageFile = null;
//                if (subImages != null) {
//                    subImageFile = new ArrayList<>(Arrays.stream(subImages).map(MultipartFile::getOriginalFilename).toList());
//                    List<Object> list = new ArrayList<>(Arrays.asList(mainImage.getOriginalFilename(), subImageFile, productDTO));
//                    return ResponseEntity.ok(list);
//                }
//                else return ResponseEntity.ok("sub image null");
//
//            }
//            return new ResponseEntity<>("product inserted failed", HttpStatus.NOT_FOUND);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }


    @Operation(summary = "Create new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createProduct(
            @RequestPart(value = "productDTO") String jsonString,
            @RequestPart(value = "mainImage") MultipartFile mainImage,
            @RequestPart(value = "subImages", required = false) MultipartFile[] subImages
    ) {
        Integer productId;
        try {
            String fileName = firebaseStorageService.uploadFile(mainImage);
            String mainImgUrl = firebaseStorageService.getImageUrl(fileName);

            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(jsonString, ProductDTO.class);
            productDTO.setImageMain(mainImgUrl);

            productId = productService.saveProduct(productDTO);
            if (productId != null && subImages != null) {
//                MultipartFile[] subImages = productRequestDTO.getSubImages();
//                if (subImages != null) {
                String[] subImgUrls = new String[subImages.length];
                for (int i = 0; i < subImages.length; i++) {
                    fileName = firebaseStorageService.uploadFile(subImages[i]);
                    subImgUrls[i] = firebaseStorageService.getImageUrl(fileName);
                }
                productImageService.saveImages(subImgUrls, productId);
//                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (productId != null) {
            return new ResponseEntity<>(productId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Product created failed!!!", HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Update existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PutMapping(value = "/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") Integer id,
            @RequestPart(value = "productDTO") String jsonString,
            @RequestPart(value = "mainImage") MultipartFile mainImage,
            @RequestPart(value = "subImages", required = false) MultipartFile[] subImages
    ) {
        Integer productId;
        try {
            String fileName = firebaseStorageService.uploadFile(mainImage);
            String mainImgUrl = firebaseStorageService.getImageUrl(fileName);

            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(jsonString, ProductDTO.class);

            productDTO.setId(id);
            productDTO.setImageMain(mainImgUrl);

            productId = productService.saveProduct(productDTO);
            if (productId != null && subImages != null) {
                String[] subImgUrls = new String[subImages.length];
                for (int i = 0; i < subImages.length; i++) {
                    fileName = firebaseStorageService.uploadFile(subImages[i]);
                    subImgUrls[i] = firebaseStorageService.getImageUrl(fileName);
                }
                productImageService.saveImages(subImgUrls, productId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (productId != null) {
            return new ResponseEntity<>(productId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product created failed!!!", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        if (!productService.deleteProduct(id)) {
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product deleted failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get Product review by product id and paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Load Review List", content = @Content(schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping("/review/{product-id}")
    public ResponseEntity<?> getProductReview(@PathVariable("product-id") Integer productId,
                                              @RequestParam("page") Optional<Integer> page) {
        System.out.println(page);
        Pageable pageable = PageRequest.of(page.orElse(0), 3);
        Map<List<ReviewDTO>, Long> listMap = reviewService
                .getReviewByPageAndProductIdAndStatus(pageable, productId, true);

        List<Object> list = new ArrayList<>();

        listMap.forEach((reviewDTOS, total) -> {
            list.add(reviewDTOS);
            list.add(total);
        });

        if (list.isEmpty()) {
            return new ResponseEntity<>("No review found!!!", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(list);
        }
    }
}
