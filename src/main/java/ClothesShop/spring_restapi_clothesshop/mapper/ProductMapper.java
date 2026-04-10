package ClothesShop.spring_restapi_clothesshop.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductResponse;
import ClothesShop.spring_restapi_clothesshop.dto.product.ProductUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Category;
import ClothesShop.spring_restapi_clothesshop.model.Product;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mapping(source = "categories", target = "categoryNames", qualifiedByName = "categoriesToNames")
    @Mapping(source = "imageUrl", target = "imageUrls", qualifiedByName = "parseImageUrls")
    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "parseFirstImageUrl")
    public abstract ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "productDetails", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Product toEntity(ProductCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "productDetails", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void updateFromRequest(ProductUpdateRequest request, @MappingTarget Product product);

    @Named("categoriesToNames")
    protected List<String> categoriesToNames(List<Category> categories) {
        if (categories == null)
            return new ArrayList<>();
        return categories.stream().map(Category::getName).toList();
    }

    @Named("parseImageUrls")
    protected List<String> parseImageUrls(String raw) {
        if (raw == null || raw.isBlank())
            return new ArrayList<>();
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

    @Named("parseFirstImageUrl")
    protected String parseFirstImageUrl(String raw) {
        List<String> urls = parseImageUrls(raw);
        return urls.isEmpty() ? null : urls.get(0);
    }
}
