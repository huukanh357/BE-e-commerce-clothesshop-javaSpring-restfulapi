package ClothesShop.spring_restapi_clothesshop.dto.productDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {

    private Long id;
    private Long productId;
    private String size;
    private String color;
    private Integer stockQuantity;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProductDetailResponse fromEntity(ProductDetail productDetail) {
        ProductDetailResponse dto = new ProductDetailResponse();
        dto.id = productDetail.getId();
        dto.productId = productDetail.getProduct().getId();
        dto.size = productDetail.getSize();
        dto.color = productDetail.getColor();
        dto.stockQuantity = productDetail.getStockQuantity();
        dto.createdAt = productDetail.getCreatedAt();
        dto.updatedAt = productDetail.getUpdatedAt();
        return dto;
    }
}