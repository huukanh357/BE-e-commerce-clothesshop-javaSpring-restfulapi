package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartResponse;
import ClothesShop.spring_restapi_clothesshop.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "user.id", target = "userId")
    CartResponse toResponse(Cart cart);
}
