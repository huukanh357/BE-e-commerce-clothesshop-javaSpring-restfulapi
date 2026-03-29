package ClothesShop.spring_restapi_clothesshop.dto.cart;

import jakarta.validation.constraints.Min;

public class CartUpdateRequest {

    @Min(value = 1, message = "User id phải lớn hơn 0")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
