package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.productDetail.ProductDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ProductDetail;

@Mapper(componentModel = "spring")
public interface ProductDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cartDetails", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductDetail toEntity(ProductDetailCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cartDetails", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(ProductDetailUpdateRequest request, @MappingTarget ProductDetail detail);

    @Mapping(source = "product.id", target = "productId")
    ProductDetailResponse toResponse(ProductDetail productDetail);
}
