package ClothesShop.spring_restapi_clothesshop.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCreateRequest {

    @NotNull(message = "ORDER_ID_REQUIRED")
    @Min(value = 1, message = "ORDER_ID_INVALID")
    private Long orderId;

    @NotNull(message = "USER_ID_REQUIRED")
    @Min(value = 1, message = "USER_ID_INVALID")
    private Long userId;

    @NotNull(message = "AMOUNT_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount phải lớn hơn 0")
    private BigDecimal amount;

    @NotNull(message = "METHOD_REQUIRED")
    private PaymentMethodEnum method;

    private PaymentStatusEnum status;
}