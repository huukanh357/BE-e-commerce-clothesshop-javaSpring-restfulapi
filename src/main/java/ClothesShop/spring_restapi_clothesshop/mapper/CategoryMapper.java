package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryResponse;
import ClothesShop.spring_restapi_clothesshop.dto.category.CategoryUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name", qualifiedByName = "trim")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name", qualifiedByName = "trim")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(CategoryUpdateRequest request, @MappingTarget Category category);

    CategoryResponse toResponse(Category category);

    @Named("trim")
    default String trim(String value) {
        return value == null ? null : value.trim();
    }
}
