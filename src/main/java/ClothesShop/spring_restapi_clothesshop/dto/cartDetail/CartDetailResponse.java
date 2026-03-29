package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import java.time.Instant;

public class CartDetailResponse {

    private Long id;
    private Long cartId;
    private Long productDetailId;
    private Integer quantity;
    private Instant createdAt;
    private Instant updatedAt;

    public static CartDetailResponse fromEntity(CartDetail cartDetail) {
        CartDetailResponse dto = new CartDetailResponse();
        dto.id = cartDetail.getId();
        dto.cartId = cartDetail.getCart().getId();
        dto.productDetailId = cartDetail.getProductDetail().getId();
        dto.quantity = cartDetail.getQuantity();
        dto.createdAt = cartDetail.getCreatedAt();
        dto.updatedAt = cartDetail.getUpdatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
