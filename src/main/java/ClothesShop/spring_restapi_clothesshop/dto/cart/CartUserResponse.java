package ClothesShop.spring_restapi_clothesshop.dto.cart;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemResponse;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartUserResponse {

    private Long id;
    private Long userId;
    private Instant createdAt;
    private Instant updatedAt;
    private List<CartItemResponse> items;

    public static CartUserResponse from(Cart cart, List<CartDetail> cartDetails) {
        CartUserResponse dto = new CartUserResponse();
        dto.id = cart.getId();
        dto.userId = cart.getUser().getId();
        dto.createdAt = cart.getCreatedAt();
        dto.updatedAt = cart.getUpdatedAt();
        dto.items = cartDetails.stream()
                .map(CartItemResponse::fromEntity)
                .toList();
        return dto;
    }

    public static CartUserResponse empty(Long userId) {
        CartUserResponse dto = new CartUserResponse();
        dto.userId = userId;
        dto.items = new ArrayList<>();
        return dto;
    }
}