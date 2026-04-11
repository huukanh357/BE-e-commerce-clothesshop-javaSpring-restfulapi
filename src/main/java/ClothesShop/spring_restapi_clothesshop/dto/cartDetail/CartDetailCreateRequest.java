package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDetailCreateRequest {

    @NotNull(message = "CART_ID_REQUIRED")
    @Min(value = 1, message = "CART_ID_INVALID")
    private Long cartId;

    @NotNull(message = "PRODUCT_DETAIL_ID_REQUIRED")
    @Min(value = 1, message = "PRODUCT_DETAIL_ID_INVALID")
    private Long productDetailId;

    @NotNull(message = "QUANTITY_REQUIRED")
    @Min(value = 1, message = "QUANTITY_INVALID")
    private Integer quantity;
}