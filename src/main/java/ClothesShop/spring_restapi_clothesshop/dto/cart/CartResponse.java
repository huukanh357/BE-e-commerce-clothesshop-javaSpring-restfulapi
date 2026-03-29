package ClothesShop.spring_restapi_clothesshop.dto.cart;

import ClothesShop.spring_restapi_clothesshop.model.Cart;
import java.time.Instant;

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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
