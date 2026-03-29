package ClothesShop.spring_restapi_clothesshop.dto.payment;

import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentUserCreateRequest {

    @NotNull(message = "Order id không được để trống")
    @Min(value = 1, message = "Order id phải lớn hơn 0")
    private Long orderId;

    @NotNull(message = "Amount không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount phải lớn hơn 0")
    private BigDecimal amount;

    @NotNull(message = "Method không được để trống")
    private PaymentMethodEnum method;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethodEnum getMethod() {
        return method;
    }

    public void setMethod(PaymentMethodEnum method) {
        this.method = method;
    }
}
