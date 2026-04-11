package ClothesShop.spring_restapi_clothesshop.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
}