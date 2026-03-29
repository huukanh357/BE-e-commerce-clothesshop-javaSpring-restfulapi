package ClothesShop.spring_restapi_clothesshop.dto.payment;

import ClothesShop.spring_restapi_clothesshop.model.Payment;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import java.math.BigDecimal;
import java.time.Instant;

public class PaymentResponse {

    private Long id;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private PaymentMethodEnum method;
    private PaymentStatusEnum status;
    private Instant createdAt;
    private Instant updatedAt;

    public static PaymentResponse fromEntity(Payment payment) {
        PaymentResponse dto = new PaymentResponse();
        dto.id = payment.getId();
        dto.orderId = payment.getOrder().getId();
        dto.userId = payment.getUser().getId();
        dto.amount = payment.getAmount();
        dto.method = payment.getMethod();
        dto.status = payment.getStatus();
        dto.createdAt = payment.getCreatedAt();
        dto.updatedAt = payment.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethodEnum getMethod() {
        return method;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
