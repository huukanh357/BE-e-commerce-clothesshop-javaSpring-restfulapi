package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.CartDetailService;
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
public class CartDetailController {

    CartDetailService cartDetailService;
@GetMapping("/api/cart-details")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllCartDetails(Pageable pageable) {
        Page<CartDetailResponse> page = cartDetailService.getAllCartDetails(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all cart details successfully", result));
    }

    @GetMapping("/api/cart-details/{id}")
    public ResponseEntity<ApiResponse<CartDetailResponse>> getCartDetailById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        CartDetailResponse cartDetail = cartDetailService.getCartDetailById(id);
        return ResponseEntity.ok(ApiResponse.success("Get cart detail successfully", cartDetail));
    }

    @GetMapping("/api/cart-details/cart/{cartId}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getCartDetailsByCartId(
            @PathVariable @Positive(message = "Cart id phải lớn hơn 0") Long cartId,
            Pageable pageable) {
        Page<CartDetailResponse> page = cartDetailService.getCartDetailsByCartId(cartId, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get cart details by cart successfully", result));
    }

    @PostMapping("/api/cart-details")
    public ResponseEntity<ApiResponse<CartDetailResponse>> createCartDetail(
            @RequestBody @Valid CartDetailCreateRequest request) {
        CartDetailResponse cartDetail = cartDetailService.createCartDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Cart detail created successfully", cartDetail));
    }

    @PutMapping("/api/cart-details/{id}")
    public ResponseEntity<ApiResponse<CartDetailResponse>> updateCartDetail(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid CartDetailUpdateRequest request) {
        CartDetailResponse cartDetail = cartDetailService.updateCartDetail(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cart detail updated successfully", cartDetail));
    }

    @DeleteMapping("/api/cart-details/{id}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        cartDetailService.deleteCartDetail(id);
        return ResponseEntity.noContent().build();
    }
}
