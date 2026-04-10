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

    @NotNull(message = "Cart id không được để trống")
    @Min(value = 1, message = "Cart id phải lớn hơn 0")
    private Long cartId;

    @NotNull(message = "Product detail id không được để trống")
    @Min(value = 1, message = "Product detail id phải lớn hơn 0")
    private Long productDetailId;

    @NotNull(message = "Quantity không được để trống")
    @Min(value = 1, message = "Quantity phải lớn hơn 0")
    private Integer quantity;
}
