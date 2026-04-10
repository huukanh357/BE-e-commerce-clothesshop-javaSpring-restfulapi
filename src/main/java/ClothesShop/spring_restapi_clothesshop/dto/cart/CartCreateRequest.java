package ClothesShop.spring_restapi_clothesshop.dto.cart;

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
public class CartCreateRequest {

    @NotNull(message = "User id không được để trống")
    @Min(value = 1, message = "User id phải lớn hơn 0")
    private Long userId;
}
