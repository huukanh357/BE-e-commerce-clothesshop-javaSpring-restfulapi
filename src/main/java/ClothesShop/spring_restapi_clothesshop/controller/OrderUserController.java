package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderResponse;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/me/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderUserController {

    OrderService orderService;
@GetMapping
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getMyOrders(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable) {
        Page<OrderResponse> page = orderService.getMyOrders(extractUserId(jwt), pageable);
        return ResponseEntity.ok(ApiResponse.success("Get my orders successfully", ResultPaginationDTO.fromPage(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getMyOrderById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        OrderResponse order = orderService.getMyOrderById(extractUserId(jwt), id);
        return ResponseEntity.ok(ApiResponse.success("Get my order successfully", order));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createMyOrder(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid OrderUserCreateRequest request) {
        OrderResponse order = orderService.createMyOrder(extractUserId(jwt), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Order created successfully", order));
    }

    private Long extractUserId(Jwt jwt) {
        Number userId = jwt.getClaim("userId");
        if (userId == null) {
            throw new IllegalArgumentException("Token không chứa userId");
        }
        return userId.longValue();
    }
}
