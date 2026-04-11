package ClothesShop.spring_restapi_clothesshop.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.ShippingInfo;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingInfoResponse {

    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingNote;
    private Instant createdAt;
    private Instant updatedAt;

    public static ShippingInfoResponse fromEntity(ShippingInfo shippingInfo) {
        if (shippingInfo == null) {
            return null;
        }
        ShippingInfoResponse dto = new ShippingInfoResponse();
        dto.id = shippingInfo.getId();
        dto.receiverName = shippingInfo.getReceiverName();
        dto.receiverPhone = shippingInfo.getReceiverPhone();
        dto.shippingAddress = shippingInfo.getShippingAddress();
        dto.shippingCity = shippingInfo.getShippingCity();
        dto.shippingNote = shippingInfo.getShippingNote();
        dto.createdAt = shippingInfo.getCreatedAt();
        dto.updatedAt = shippingInfo.getUpdatedAt();
        return dto;
    }
}