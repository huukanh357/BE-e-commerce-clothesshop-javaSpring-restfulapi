package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemResponse;
import ClothesShop.spring_restapi_clothesshop.dto.orderItem.OrderItemUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Category;
import ClothesShop.spring_restapi_clothesshop.model.OrderItem;
import ClothesShop.spring_restapi_clothesshop.model.Product;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderItem toEntity(OrderItemCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromRequest(OrderItemUpdateRequest request, @MappingTarget OrderItem orderItem);

    @Mapping(source = "order.id", target = "orderId")
    OrderItemResponse toResponse(OrderItem orderItem);

    @Mapping(source = "product", target = "product")
    OrderItemResponse.ProductDetailInfo toProductDetailInfo(ProductDetail productDetail);

    @Mapping(source = "categories", target = "categoryNames", qualifiedByName = "categoriesToNames")
    OrderItemResponse.ProductInfo toProductInfo(Product product);

    @Named("categoriesToNames")
    default List<String> categoriesToNames(List<Category> categories) {
        if (categories == null)
            return new ArrayList<>();
        return categories.stream().map(Category::getName).toList();
    }
}
