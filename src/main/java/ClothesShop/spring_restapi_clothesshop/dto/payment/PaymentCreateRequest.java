package ClothesShop.spring_restapi_clothesshop.dto.payment;

import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentCreateRequest {

    @NotNull(message = "Order id không được để trống")
    @Min(value = 1, message = "Order id phải lớn hơn 0")
    private Long orderId;

    @NotNull(message = "User id không được để trống")
    @Min(value = 1, message = "User id phải lớn hơn 0")
    private Long userId;

    @NotNull(message = "Amount không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount phải lớn hơn 0")
    private BigDecimal amount;

    @NotNull(message = "Method không được để trống")
    private PaymentMethodEnum method;

    private PaymentStatusEnum status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }
}
