package ClothesShop.spring_restapi_clothesshop.dto.productDetail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailUpdateRequest {

    @Size(max = 20, message = "Size tối đa 20 ký tự")
    private String size;

    @Size(max = 50, message = "Color tối đa 50 ký tự")
    private String color;

    @Min(value = 0, message = "Stock quantity không được âm")
    private Integer stockQuantity;
}
