package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}
