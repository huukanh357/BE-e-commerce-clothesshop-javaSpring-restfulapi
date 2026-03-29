package ClothesShop.spring_restapi_clothesshop.dto.payment;

import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class PaymentUpdateRequest {

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount phải lớn hơn 0")
    private BigDecimal amount;

    private PaymentMethodEnum method;

    private PaymentStatusEnum status;

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
