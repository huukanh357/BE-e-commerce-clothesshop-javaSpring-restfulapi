package ClothesShop.spring_restapi_clothesshop.controller;

import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemResponse;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.OrderItemService;
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
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/api/order-items")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllOrderItems(Pageable pageable) {
        Page<OrderItemResponse> page = orderItemService.getAllOrderItems(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all order items successfully", result));
    }

    @GetMapping("/api/order-items/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> getOrderItemById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        OrderItemResponse orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(ApiResponse.success("Get order item successfully", orderItem));
    }

    @GetMapping("/api/order-items/order/{orderId}")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getOrderItemsByOrderId(
            @PathVariable @Positive(message = "Order id phải lớn hơn 0") Long orderId,
            Pageable pageable) {
        Page<OrderItemResponse> page = orderItemService.getOrderItemsByOrderId(orderId, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get order items by order successfully", result));
    }

    @PostMapping("/api/order-items")
    public ResponseEntity<ApiResponse<OrderItemResponse>> createOrderItem(
            @RequestBody @Valid OrderItemCreateRequest request) {
        OrderItemResponse orderItem = orderItemService.createOrderItem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Order item created successfully", orderItem));
    }

    @PutMapping("/api/order-items/{id}")
    public ResponseEntity<ApiResponse<OrderItemResponse>> updateOrderItem(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid OrderItemUpdateRequest request) {
        OrderItemResponse orderItem = orderItemService.updateOrderItem(id, request);
        return ResponseEntity.ok(ApiResponse.success("Order item updated successfully", orderItem));
    }

    @DeleteMapping("/api/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
