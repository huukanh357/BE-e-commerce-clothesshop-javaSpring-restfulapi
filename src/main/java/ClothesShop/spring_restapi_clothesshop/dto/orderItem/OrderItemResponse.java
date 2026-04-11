package ClothesShop.spring_restapi_clothesshop.dto.orderItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

    private Long id;
    private Long orderId;
    private Integer quantity;
    private BigDecimal price;
    private Instant createdAt;
    private ProductDetailInfo productDetail;

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.id = orderItem.getId();
        dto.orderId = orderItem.getOrder().getId();
        dto.quantity = orderItem.getQuantity();
        dto.price = orderItem.getPrice();
        dto.createdAt = orderItem.getCreatedAt();

        ProductDetailInfo pdInfo = new ProductDetailInfo();
        pdInfo.id = orderItem.getProductDetail().getId();
        pdInfo.size = orderItem.getProductDetail().getSize();
        pdInfo.color = orderItem.getProductDetail().getColor();
        pdInfo.stockQuantity = orderItem.getProductDetail().getStockQuantity();

        ProductInfo pInfo = new ProductInfo();
        pInfo.id = orderItem.getProductDetail().getProduct().getId();
        pInfo.name = orderItem.getProductDetail().getProduct().getName();
        pInfo.description = orderItem.getProductDetail().getProduct().getDescription();
        pInfo.price = orderItem.getProductDetail().getProduct().getPrice();
        pInfo.categoryNames = orderItem.getProductDetail().getProduct().getCategories()
                .stream().map(c -> c.getName()).collect(Collectors.toList());
        pInfo.imageUrl = orderItem.getProductDetail().getProduct().getImageUrl();

        pdInfo.product = pInfo;
        dto.productDetail = pdInfo;

        return dto;
    }
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductDetailInfo {
        private Long id;
        private String size;
        private String color;
        private Integer stockQuantity;
        private ProductInfo product;
    }
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductInfo {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private List<String> categoryNames;
        private String imageUrl;
    }
}