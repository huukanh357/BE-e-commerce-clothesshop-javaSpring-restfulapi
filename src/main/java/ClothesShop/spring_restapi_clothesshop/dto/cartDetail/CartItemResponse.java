package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {

    private Long itemId;
    private Long productId;
    private Long productDetailId;
    private String productName;
    private String imageUrl;
    private String size;
    private String color;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;

    public static CartItemResponse fromEntity(CartDetail cartDetail) {
        CartItemResponse dto = new CartItemResponse();
        ProductDetail pd = cartDetail.getProductDetail();
        Product p = pd.getProduct();

        dto.itemId = cartDetail.getId();
        dto.productDetailId = pd.getId();
        dto.productId = p.getId();
        dto.productName = p.getName();
        dto.imageUrl = p.getImageUrl();
        dto.size = pd.getSize();
        dto.color = pd.getColor();
        dto.quantity = cartDetail.getQuantity();
        dto.unitPrice = p.getPrice();
        dto.totalPrice = dto.unitPrice.multiply(BigDecimal.valueOf(dto.quantity));
        dto.createdAt = cartDetail.getCreatedAt();
        dto.updatedAt = cartDetail.getUpdatedAt();
        return dto;
    }
}