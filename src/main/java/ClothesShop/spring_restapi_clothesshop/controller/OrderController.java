package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderResponse;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;
import ClothesShop.spring_restapi_clothesshop.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    OrderService orderService;
@GetMapping("/api/orders")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllOrders(Pageable pageable) {
        Page<OrderResponse> page = orderService.getAllOrders(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all orders successfully", result));
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Get order successfully", order));
    }

    @GetMapping("/api/orders/user/{userId}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getOrdersByUserId(
            @PathVariable @Positive(message = "User id phải lớn hơn 0") Long userId,
            Pageable pageable) {
        Page<OrderResponse> page = orderService.getOrdersByUserId(userId, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get orders by user successfully", result));
    }

    @GetMapping("/api/orders/status")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getOrdersByStatus(
            @RequestParam OrderStatusEnum status,
            Pageable pageable) {
        Page<OrderResponse> page = orderService.getOrdersByStatus(status, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get orders by status successfully", result));
    }

    @PostMapping("/api/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody @Valid OrderCreateRequest request) {
        OrderResponse order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Order created successfully", order));
    }

    @PutMapping("/api/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid OrderUpdateRequest request) {
        OrderResponse order = orderService.updateOrder(id, request);
        return ResponseEntity.ok(ApiResponse.success("Order updated successfully", order));
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

