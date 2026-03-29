package ClothesShop.spring_restapi_clothesshop.dto.category;

import ClothesShop.spring_restapi_clothesshop.model.Category;
import java.time.Instant;

public class CategoryResponse {

    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public static CategoryResponse fromEntity(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.id = category.getId();
        response.name = category.getName();
        response.createdAt = category.getCreatedAt();
        response.updatedAt = category.getUpdatedAt();
        return response;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
