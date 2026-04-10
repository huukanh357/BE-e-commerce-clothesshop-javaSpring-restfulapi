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

    @NotNull(message = "Order id không được để trống")
    @Min(value = 1, message = "Order id phải lớn hơn 0")
    private Long orderId;

    @NotNull(message = "Product detail id không được để trống")
    @Min(value = 1, message = "Product detail id phải lớn hơn 0")
    private Long productDetailId;

    @NotNull(message = "Quantity không được để trống")
    @Min(value = 1, message = "Quantity phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Price không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
    private BigDecimal price;
}
