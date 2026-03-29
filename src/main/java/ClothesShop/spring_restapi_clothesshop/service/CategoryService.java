package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryResponse;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest request);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse getCategoryByName(String name);

    Page<CategoryResponse> getAllCategories(Pageable pageable);

    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategory(Long id);

    boolean existsByName(String name);
}
