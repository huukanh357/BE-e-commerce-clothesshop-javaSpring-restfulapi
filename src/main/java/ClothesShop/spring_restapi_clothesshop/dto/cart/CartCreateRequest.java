package ClothesShop.spring_restapi_clothesshop.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartCreateRequest {

    @NotNull(message = "User id không được để trống")
    @Min(value = 1, message = "User id phải lớn hơn 0")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
