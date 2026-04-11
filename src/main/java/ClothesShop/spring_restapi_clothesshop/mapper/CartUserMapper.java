package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ClothesShop.spring_restapi_clothesshop.dto.cart.CartUserResponse;
import ClothesShop.spring_restapi_clothesshop.model.Cart;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = { CartItemMapper.class })
public interface CartUserMapper {

    @Mapping(source = "cart.id", target = "id")
    @Mapping(source = "cart.user.id", target = "userId")
    @Mapping(source = "cart.createdAt", target = "createdAt")
    @Mapping(source = "cart.updatedAt", target = "updatedAt")
    @Mapping(source = "cartDetails", target = "items")
    CartUserResponse toResponse(Cart cart, List<CartDetail> cartDetails);

    default CartUserResponse empty(Long userId) {
        CartUserResponse dto = new CartUserResponse();
        dto.setUserId(userId);
        dto.setItems(new ArrayList<>());
        return dto;
    }
}