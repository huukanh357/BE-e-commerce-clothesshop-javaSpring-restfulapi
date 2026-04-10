package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryResponse;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Category;
import ClothesShop.spring_restapi_clothesshop.repository.CategoryRepository;
import ClothesShop.spring_restapi_clothesshop.service.CategoryService;
import ClothesShop.spring_restapi_clothesshop.mapper.CategoryMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    @CacheEvict(cacheNames = { "categoryById", "categoryByName", "categoriesPage" }, allEntries = true)
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category", "name", request.getName());
        }

        Category category = categoryMapper.toEntity(request);
        category.setName(category.getName().trim());

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "categoryById", key = "#id")
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(findCategoryByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "categoryByName", key = "#name")
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "categoriesPage")
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
    }

    @Override
    @CacheEvict(cacheNames = { "categoryById", "categoryByName", "categoriesPage" }, allEntries = true)
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = findCategoryByIdOrThrow(id);

        if (request.getName() != null) {
            String newName = request.getName().trim();
            if (!newName.isEmpty() && !newName.equals(category.getName()) && categoryRepository.existsByName(newName)) {
                throw new DuplicateResourceException("Category", "name", newName);
            }
            if (!newName.isEmpty()) {
                categoryMapper.updateFromRequest(request, category);
            }
        }

        Category updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Override
    @CacheEvict(cacheNames = { "categoryById", "categoryByName", "categoriesPage" }, allEntries = true)
    public void deleteCategory(Long id) {
        findCategoryByIdOrThrow(id);
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    private Category findCategoryByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }
}
