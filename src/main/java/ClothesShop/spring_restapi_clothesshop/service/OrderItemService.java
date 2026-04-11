package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemResponse;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {

    OrderItemResponse createOrderItem(OrderItemCreateRequest request);

    OrderItemResponse getOrderItemById(Long id);

    Page<OrderItemResponse> getAllOrderItems(Pageable pageable);

    Page<OrderItemResponse> getOrderItemsByOrderId(Long orderId, Pageable pageable);

    OrderItemResponse updateOrderItem(Long id, OrderItemUpdateRequest request);

    void deleteOrderItem(Long id);
}