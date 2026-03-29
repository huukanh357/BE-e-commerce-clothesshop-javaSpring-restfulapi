package ClothesShop.spring_restapi_clothesshop.dto.productDetail;

import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;
import java.time.Instant;

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

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
