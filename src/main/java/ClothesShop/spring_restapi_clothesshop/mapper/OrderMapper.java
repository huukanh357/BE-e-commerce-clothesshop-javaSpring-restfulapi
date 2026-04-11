package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderResponse;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.order.OrderUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Order;

@Mapper(componentModel = "spring", uses = { ShippingInfoMapper.class })
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(source = "shippingInfo", target = "shippingInfo")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(source = "shippingInfo", target = "shippingInfo")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderUserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "shippingInfo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(OrderUpdateRequest request, @MappingTarget Order order);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cart.id", target = "cartId")
    OrderResponse toResponse(Order order);
}