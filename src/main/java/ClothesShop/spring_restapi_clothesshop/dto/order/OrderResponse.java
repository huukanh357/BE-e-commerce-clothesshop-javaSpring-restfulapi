package ClothesShop.spring_restapi_clothesshop.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Order;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}