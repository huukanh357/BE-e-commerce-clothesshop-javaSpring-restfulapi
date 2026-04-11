package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductFilterRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductResponse;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {

    ProductService productService;
@GetMapping("/api/products")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllProducts(Pageable pageable) {
        Page<ProductResponse> page = productService.getAllProducts(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all products successfully", result));
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Get product successfully", product));
    }

    @GetMapping("/api/products/name/{name}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByName(@PathVariable String name) {
        ProductResponse product = productService.getProductByName(name);
        return ResponseEntity.ok(ApiResponse.success("Get product successfully", product));
    }

    @GetMapping("/api/products/category/{categoryName}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getProductsByCategory(
            @PathVariable String categoryName,
            Pageable pageable) {
        Page<ProductResponse> page = productService.getProductsByCategory(categoryName, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get products by category successfully", result));
    }

    @GetMapping("/api/products/filter")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> filterProducts(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> sizes,
            Pageable pageable) {
        ProductFilterRequest filterRequest = new ProductFilterRequest(minPrice, maxPrice, categories, sizes);
        Page<ProductResponse> page = productService.filterProducts(filterRequest, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Filter products successfully", result));
    }

    @PostMapping("/api/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody @Valid ProductCreateRequest request) {
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Product created successfully", product));
    }

    @PostMapping(value = "/api/products/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductResponse>> uploadProductImages(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestParam("files") List<MultipartFile> files) {
        ProductResponse product = productService.uploadProductImages(id, files);
        return ResponseEntity.ok(ApiResponse.success("Product images uploaded successfully", product));
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid ProductUpdateRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", product));
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/products/check/name")
    public ResponseEntity<ApiResponse<Boolean>> checkProductNameExists(@RequestParam String name) {
        boolean exists = productService.existsByName(name);
        return ResponseEntity
                .ok(ApiResponse.success(exists ? "Product name already exists" : "Product name available", exists));
    }
}
