package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemUpdateRequest {

    @NotNull(message = "quantity không được để trống")
    @Min(value = 0, message = "quantity phải >= 0")
    @Max(value = 99, message = "quantity không được vượt quá 99")
    private Integer quantity;
}
