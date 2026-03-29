package ClothesShop.spring_restapi_clothesshop.dto.productDetail;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDetailCreateRequest {

    @NotNull(message = "Product id không được để trống")
    @Min(value = 1, message = "Product id phải lớn hơn 0")
    private Long productId;

    @NotBlank(message = "Size không được để trống")
    @Size(max = 20, message = "Size tối đa 20 ký tự")
    private String size;

    @Size(max = 50, message = "Color tối đa 50 ký tự")
    private String color;

    @NotNull(message = "Stock quantity không được để trống")
    @Min(value = 0, message = "Stock quantity không được âm")
    private Integer stockQuantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
