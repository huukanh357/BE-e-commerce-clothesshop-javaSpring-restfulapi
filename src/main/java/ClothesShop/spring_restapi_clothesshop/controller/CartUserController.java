package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartUserController {

    CartService cartService;
@GetMapping
    public ResponseEntity<ApiResponse<CartUserResponse>> getMyCart(
            @AuthenticationPrincipal Jwt jwt) {
        CartUserResponse cart = cartService.getMyCart(extractUserId(jwt));
        return ResponseEntity.ok(ApiResponse.success("Lấy giỏ hàng thành công", cart));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartUserResponse>> addItem(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CartItemRequest request) {
        CartUserResponse cart = cartService.addItemToMyCart(extractUserId(jwt), request);
        return ResponseEntity.ok(ApiResponse.success("Thêm sản phẩm vào giỏ hàng thành công", cart));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartUserResponse>> updateItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long itemId,
            @Valid @RequestBody CartItemUpdateRequest request) {
        CartUserResponse cart = cartService.updateMyCartItem(extractUserId(jwt), itemId, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật giỏ hàng thành công", cart));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartUserResponse>> deleteItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long itemId) {
        CartUserResponse cart = cartService.deleteMyCartItem(extractUserId(jwt), itemId);
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm khỏi giỏ hàng thành công", cart));
    }

    @DeleteMapping("/items")
    public ResponseEntity<ApiResponse<CartUserResponse>> clearCart(
            @AuthenticationPrincipal Jwt jwt) {
        CartUserResponse cart = cartService.clearMyCart(extractUserId(jwt));
        return ResponseEntity.ok(ApiResponse.success("Đã xóa toàn bộ giỏ hàng", cart));
    }

    private Long extractUserId(Jwt jwt) {
        Number userId = jwt.getClaim("userId");
        if (userId == null) {
            throw new IllegalArgumentException("Token không chứa userId");
        }
        return userId.longValue();
    }
}

