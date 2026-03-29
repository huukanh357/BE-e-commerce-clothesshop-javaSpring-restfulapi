package ClothesShop.spring_restapi_clothesshop.dto.order;

import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderResponse {

    private Long id;
    private Long userId;
    private Long cartId;
    private BigDecimal totalAmount;
    private ShippingInfoResponse shippingInfo;
    private OrderStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    public static OrderResponse fromEntity(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.id = order.getId();
        dto.userId = order.getUser().getId();
        dto.cartId = order.getCart() != null ? order.getCart().getId() : null;
        dto.totalAmount = order.getTotalAmount();
        dto.shippingInfo = ShippingInfoResponse.fromEntity(order.getShippingInfo());
        dto.status = order.getStatus();
        dto.createdAt = order.getCreatedAt();
        dto.updatedAt = order.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public ShippingInfoResponse getShippingInfo() {
        return shippingInfo;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
