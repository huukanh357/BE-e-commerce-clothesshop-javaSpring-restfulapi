package ClothesShop.spring_restapi_clothesshop.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {

    @NotNull(message = "USER_ID_REQUIRED")
    @Min(value = 1, message = "USER_ID_INVALID")
    private Long userId;

    @Min(value = 1, message = "CART_ID_INVALID")
    private Long cartId;

    @NotNull(message = "TOTAL_AMOUNT_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount phải lớn hơn 0")
    private BigDecimal totalAmount;

    @NotNull(message = "SHIPPING_INFO_REQUIRED")
    @Valid
    private ShippingInfoRequest shippingInfo;

    private OrderStatusEnum status;
}