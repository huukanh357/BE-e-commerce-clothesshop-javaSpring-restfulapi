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

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 0, message = "QUANTITY_NON_NEGATIVE")
    @Max(value = 99, message = "QUANTITY_TOO_LARGE")
    private Integer quantity;
}