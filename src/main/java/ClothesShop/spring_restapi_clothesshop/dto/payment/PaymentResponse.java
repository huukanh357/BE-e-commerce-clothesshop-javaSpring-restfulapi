package ClothesShop.spring_restapi_clothesshop.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Payment;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentMethodEnum;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PaymentStatusEnum;
import java.math.BigDecimal;
import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}
