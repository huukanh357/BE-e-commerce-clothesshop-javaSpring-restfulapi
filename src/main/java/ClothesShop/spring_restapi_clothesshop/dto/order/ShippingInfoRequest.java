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

    @NotBlank(message = "Receiver name không được để trống")
    @Size(max = 100, message = "Receiver name tối đa 100 ký tự")
    private String receiverName;

    @NotBlank(message = "Receiver phone không được để trống")
    @Size(max = 20, message = "Receiver phone tối đa 20 ký tự")
    private String receiverPhone;

    @NotBlank(message = "Shipping address không được để trống")
    @Size(max = 255, message = "Shipping address tối đa 255 ký tự")
    private String shippingAddress;

    @NotBlank(message = "Shipping city không được để trống")
    @Size(max = 100, message = "Shipping city tối đa 100 ký tự")
    private String shippingCity;

    @Size(max = 500, message = "Shipping note tối đa 500 ký tự")
    private String shippingNote;
}
