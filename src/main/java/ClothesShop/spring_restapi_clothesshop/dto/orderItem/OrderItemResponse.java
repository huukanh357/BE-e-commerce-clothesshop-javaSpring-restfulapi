package ClothesShop.spring_restapi_clothesshop.dto.orderItem;

import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
        
        // Map ProductDetail with nested Product
        ProductDetailInfo pdInfo = new ProductDetailInfo();
        pdInfo.id = orderItem.getProductDetail().getId();
        pdInfo.size = orderItem.getProductDetail().getSize();
        pdInfo.color = orderItem.getProductDetail().getColor();
        pdInfo.stockQuantity = orderItem.getProductDetail().getStockQuantity();
        
        // Map nested Product info
        ProductInfo pInfo = new ProductInfo();
        pInfo.id = orderItem.getProductDetail().getProduct().getId();
        pInfo.name = orderItem.getProductDetail().getProduct().getName();
        pInfo.description = orderItem.getProductDetail().getProduct().getDescription();
        pInfo.price = orderItem.getProductDetail().getProduct().getPrice();
        pInfo.categoryNames = orderItem.getProductDetail().getProduct().getCategories()
                .stream().map(c -> c.getName()).collect(Collectors.toList());
        // TODO: parse imageUrl if complex, for now use first or null
        pInfo.imageUrl = orderItem.getProductDetail().getProduct().getImageUrl();
        
        pdInfo.product = pInfo;
        dto.productDetail = pdInfo;
        
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ProductDetailInfo getProductDetail() {
        return productDetail;
    }

    // Inner class for ProductDetail with nested Product
    public static class ProductDetailInfo {
        private Long id;
        private String size;
        private String color;
        private Integer stockQuantity;
        private ProductInfo product;

        public Long getId() {
            return id;
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

        public ProductInfo getProduct() {
            return product;
        }
    }

    // Inner class for Product info
    public static class ProductInfo {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private List<String> categoryNames;
        private String imageUrl;

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public List<String> getCategoryNames() {
            return categoryNames;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
