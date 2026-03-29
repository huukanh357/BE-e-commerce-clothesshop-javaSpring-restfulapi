package ClothesShop.spring_restapi_clothesshop.dto.cartDetail;

import jakarta.validation.constraints.Min;

public class CartDetailUpdateRequest {

    @Min(value = 1, message = "Quantity phải lớn hơn 0")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
