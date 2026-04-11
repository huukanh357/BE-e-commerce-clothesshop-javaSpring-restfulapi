package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.order.OrderCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderResponse;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse getOrderById(Long id);

    Page<OrderResponse> getAllOrders(Pageable pageable);

    Page<OrderResponse> getOrdersByUserId(Long userId, Pageable pageable);

    Page<OrderResponse> getOrdersByStatus(OrderStatusEnum status, Pageable pageable);

    OrderResponse updateOrder(Long id, OrderUpdateRequest request);

    void deleteOrder(Long id);
    Page<OrderResponse> getMyOrders(Long userId, Pageable pageable);

    OrderResponse getMyOrderById(Long userId, Long orderId);

    OrderResponse createMyOrder(Long userId, OrderUserCreateRequest request);
}
