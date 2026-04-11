package ClothesShop.spring_restapi_clothesshop.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {

    @Min(value = 1, message = "CART_ID_INVALID")
    private Long cartId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount phải lớn hơn 0")
    private BigDecimal totalAmount;

    private OrderStatusEnum status;
}