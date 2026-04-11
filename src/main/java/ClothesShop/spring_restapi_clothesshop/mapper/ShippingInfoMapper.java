package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ClothesShop.spring_restapi_clothesshop.dto.order.ShippingInfoRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.ShippingInfoResponse;
import ClothesShop.spring_restapi_clothesshop.model.ShippingInfo;

@Mapper(componentModel = "spring")
public interface ShippingInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ShippingInfo toEntity(ShippingInfoRequest request);

    ShippingInfoResponse toResponse(ShippingInfo shippingInfo);
}