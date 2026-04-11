package ClothesShop.spring_restapi_clothesshop.dto.orderItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemCreateRequest {

    @NotNull(message = "ORDER_ID_REQUIRED")
    @Min(value = 1, message = "ORDER_ID_INVALID")
    private Long orderId;

    @NotNull(message = "PRODUCT_DETAIL_ID_REQUIRED")
    @Min(value = 1, message = "PRODUCT_DETAIL_ID_INVALID")
    private Long productDetailId;

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 1, message = "QUANTITY_INVALID")
    private Integer quantity;

    @NotNull(message = "PRICE_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
    private BigDecimal price;
}