package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.CartService;
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
public class CartController {

    CartService cartService;

    @GetMapping("/api/carts")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllCarts(Pageable pageable) {
        Page<CartResponse> page = cartService.getAllCarts(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Lấy tất cả giỏ hàng thành công", result));
    }

    @GetMapping("/api/carts/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartById(
            @PathVariable @Positive(message = "ID phải lớn hơn 0") Long id) {
        CartResponse cart = cartService.getCartById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy giỏ hàng thành công", cart));
    }

    @GetMapping("/api/carts/user/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCartByUserId(
            @PathVariable @Positive(message = "ID người dùng phải lớn hơn 0") Long userId) {
        CartResponse cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Lấy giỏ hàng theo người dùng thành công", cart));
    }

    @PostMapping("/api/carts")
    public ResponseEntity<ApiResponse<CartResponse>> createCart(
            @RequestBody @Valid CartCreateRequest request) {
        CartResponse cart = cartService.createCart(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Tạo giỏ hàng thành công", cart));
    }

    @PutMapping("/api/carts/{id}")
    public ResponseEntity<ApiResponse<CartResponse>> updateCart(
            @PathVariable @Positive(message = "ID phải lớn hơn 0") Long id,
            @RequestBody @Valid CartUpdateRequest request) {
        CartResponse cart = cartService.updateCart(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật giỏ hàng thành công", cart));
    }

    @DeleteMapping("/api/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable @Positive(message = "ID phải lớn hơn 0") Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
