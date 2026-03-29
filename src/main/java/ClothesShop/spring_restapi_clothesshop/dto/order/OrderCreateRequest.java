package ClothesShop.spring_restapi_clothesshop.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import java.math.BigDecimal;

import ClothesShop.spring_restapi_clothesshop.model.ENUM.OrderStatusEnum;

public class OrderCreateRequest {

    @NotNull(message = "User id không được để trống")
    @Min(value = 1, message = "User id phải lớn hơn 0")
    private Long userId;

    @Min(value = 1, message = "Cart id phải lớn hơn 0")
    private Long cartId;

    @NotNull(message = "Total amount không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount phải lớn hơn 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Shipping info không được để trống")
    @Valid
    private ShippingInfoRequest shippingInfo;

    private OrderStatusEnum status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShippingInfoRequest getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfoRequest shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
