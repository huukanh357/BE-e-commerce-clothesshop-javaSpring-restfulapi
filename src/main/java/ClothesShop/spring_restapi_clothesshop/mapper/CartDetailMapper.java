package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailResponse;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartDetailUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "productDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CartDetail toEntity(CartDetailCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "productDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(CartDetailUpdateRequest request, @MappingTarget CartDetail cartDetail);

    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "productDetail.id", target = "productDetailId")
    CartDetailResponse toResponse(CartDetail cartDetail);
}