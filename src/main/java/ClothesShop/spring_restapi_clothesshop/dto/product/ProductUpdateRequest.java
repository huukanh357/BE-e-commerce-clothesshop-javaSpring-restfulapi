package ClothesShop.spring_restapi_clothesshop.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {

    private List<@NotBlank(message = "CATEGORY_NAME_REQUIRED") @Size(max = 100, message = "CATEGORY_NAME_TOO_LONG") String> categoryNames;

    @Size(max = 255, message = "NAME_TOO_LONG_255")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
    private BigDecimal price;

    @Min(value = 0, message = "STOCK_QUANTITY_INVALID")
    private Integer stockQuantity;

    @Size(max = 255, message = "IMAGE_URL_TOO_LONG")
    private String imageUrl;
}