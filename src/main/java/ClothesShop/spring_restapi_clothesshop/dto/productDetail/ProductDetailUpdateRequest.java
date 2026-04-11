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

    @Size(max = 20, message = "SIZE_TOO_LONG")
    private String size;

    @Size(max = 50, message = "COLOR_TOO_LONG")
    private String color;

    @Min(value = 0, message = "STOCK_QUANTITY_INVALID")
    private Integer stockQuantity;
}