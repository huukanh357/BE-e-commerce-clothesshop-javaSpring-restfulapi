package ClothesShop.spring_restapi_clothesshop.dto.product;

import ClothesShop.spring_restapi_clothesshop.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Long id;
    private List<String> categoryNames;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private List<String> imageUrls;
    private Instant createdAt;
    private Instant updatedAt;

    public static ProductResponse fromEntity(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.id = product.getId();
        dto.categoryNames = product.getCategories().stream()
                .map(category -> category.getName())
                .collect(Collectors.toList());
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.price = product.getPrice();
        dto.stockQuantity = product.getStockQuantity();
        dto.imageUrls = parseImageUrls(product.getImageUrl());
        dto.imageUrl = dto.imageUrls.isEmpty() ? null : dto.imageUrls.get(0);
        dto.createdAt = product.getCreatedAt();
        dto.updatedAt = product.getUpdatedAt();
        return dto;
    }

    private static List<String> parseImageUrls(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ArrayList<>();
        }

        String trimmed = raw.trim();
        if (trimmed.startsWith("[")) {
            try {
                return OBJECT_MAPPER.readValue(trimmed, new TypeReference<List<String>>() {
                });
            } catch (Exception ignored) {
                return List.of(trimmed);
            }
        }

        return List.of(trimmed);
    }

    public Long getId() {
        return id;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
