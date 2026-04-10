package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ClothesShop.spring_restapi_clothesshop.dto.cartDetail.CartItemResponse;
import ClothesShop.spring_restapi_clothesshop.model.CartDetail;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class CartItemMapper {

    @Mapping(source = "id", target = "itemId")
    @Mapping(source = "productDetail.id", target = "productDetailId")
    @Mapping(source = "productDetail.product.id", target = "productId")
    @Mapping(source = "productDetail.product.name", target = "productName")
    @Mapping(source = "productDetail.product.imageUrl", target = "imageUrl")
    @Mapping(source = "productDetail.size", target = "size")
    @Mapping(source = "productDetail.color", target = "color")
    @Mapping(source = "productDetail.product.price", target = "unitPrice")
    @Mapping(target = "totalPrice", ignore = true)
    public abstract CartItemResponse toResponse(CartDetail cartDetail);

    @AfterMapping
    protected void computeTotalPrice(CartDetail source, @MappingTarget CartItemResponse target) {
        if (target.getUnitPrice() != null && target.getQuantity() != null) {
            target.setTotalPrice(target.getUnitPrice().multiply(BigDecimal.valueOf(target.getQuantity())));
        }
    }
}
