package ClothesShop.spring_restapi_clothesshop.dto.order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingInfoRequest {

    @NotBlank(message = "RECEIVER_NAME_REQUIRED")
    @Size(max = 100, message = "RECEIVER_NAME_TOO_LONG")
    private String receiverName;

    @NotBlank(message = "RECEIVER_PHONE_REQUIRED")
    @Size(max = 20, message = "RECEIVER_PHONE_TOO_LONG")
    private String receiverPhone;

    @NotBlank(message = "SHIPPING_ADDRESS_REQUIRED")
    @Size(max = 255, message = "SHIPPING_ADDRESS_TOO_LONG")
    private String shippingAddress;

    @NotBlank(message = "SHIPPING_CITY_REQUIRED")
    @Size(max = 100, message = "SHIPPING_CITY_TOO_LONG")
    private String shippingCity;

    @Size(max = 500, message = "SHIPPING_NOTE_TOO_LONG")
    private String shippingNote;
}