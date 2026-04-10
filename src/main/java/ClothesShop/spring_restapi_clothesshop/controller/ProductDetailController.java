package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.ProductDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductDetailController {

    ProductDetailService productDetailService;
@GetMapping("/api/product-details")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllProductDetails(Pageable pageable) {
        Page<ProductDetailResponse> page = productDetailService.getAllProductDetails(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all product details successfully", result));
    }

    @GetMapping("/api/product-details/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetailById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        ProductDetailResponse detail = productDetailService.getProductDetailById(id);
        return ResponseEntity.ok(ApiResponse.success("Get product detail successfully", detail));
    }

    @GetMapping("/api/product-details/product/{productId}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getProductDetailsByProductId(
            @PathVariable @Positive(message = "Product id phải lớn hơn 0") Long productId,
            Pageable pageable) {
        Page<ProductDetailResponse> page = productDetailService.getProductDetailsByProductId(productId, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get product details by product id successfully", result));
    }

    @PostMapping("/api/product-details")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> createProductDetail(
            @RequestBody @Valid ProductDetailCreateRequest request) {
        ProductDetailResponse detail = productDetailService.createProductDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Product detail created successfully", detail));
    }

    @PutMapping("/api/product-details/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> updateProductDetail(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid ProductDetailUpdateRequest request) {
        ProductDetailResponse detail = productDetailService.updateProductDetail(id, request);
        return ResponseEntity.ok(ApiResponse.success("Product detail updated successfully", detail));
    }

    @DeleteMapping("/api/product-details/{id}")
    public ResponseEntity<Void> deleteProductDetail(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        productDetailService.deleteProductDetail(id);
        return ResponseEntity.noContent().build();
    }
}

