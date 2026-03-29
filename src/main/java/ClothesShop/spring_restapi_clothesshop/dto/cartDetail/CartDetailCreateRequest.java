package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartDetailCreateRequest {

    @NotNull(message = "Cart id không được để trống")
    @Min(value = 1, message = "Cart id phải lớn hơn 0")
    private Long cartId;

    @NotNull(message = "Product detail id không được để trống")
    @Min(value = 1, message = "Product detail id phải lớn hơn 0")
    private Long productDetailId;

    @NotNull(message = "Quantity không được để trống")
    @Min(value = 1, message = "Quantity phải lớn hơn 0")
    private Integer quantity;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(Long productDetailId) {
        this.productDetailId = productDetailId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
