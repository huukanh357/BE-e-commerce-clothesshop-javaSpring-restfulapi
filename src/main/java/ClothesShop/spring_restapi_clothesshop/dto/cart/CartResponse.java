package ClothesShop.spring_restapi_clothesshop.dto.cart;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {

    private Long id;
    private Long userId;
    private Instant createdAt;
    private Instant updatedAt;

    public static CartResponse fromEntity(Cart cart) {
        CartResponse dto = new CartResponse();
        dto.id = cart.getId();
        dto.userId = cart.getUser().getId();
        dto.createdAt = cart.getCreatedAt();
        dto.updatedAt = cart.getUpdatedAt();
        return dto;
    }
}