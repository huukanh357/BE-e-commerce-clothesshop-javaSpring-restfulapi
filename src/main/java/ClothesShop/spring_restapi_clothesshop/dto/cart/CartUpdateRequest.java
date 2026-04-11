package ClothesShop.spring_restapi_clothesshop.dto.cart;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartUpdateRequest {

    @Min(value = 1, message = "USER_ID_INVALID")
    private Long userId;
}