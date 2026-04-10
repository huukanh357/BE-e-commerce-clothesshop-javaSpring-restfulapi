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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {

    @NotEmpty(message = "Categories không được để trống")
    private List<@NotBlank(message = "Category name không được để trống") @Size(max = 100, message = "Category name tối đa 100 ký tự") String> categoryNames;

    @NotBlank(message = "Name không được để trống")
    @Size(max = 255, message = "Name tối đa 255 ký tự")
    private String name;

    private String description;

    @NotNull(message = "Price không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Stock quantity không được để trống")
    @Min(value = 0, message = "Stock quantity không được âm")
    private Integer stockQuantity;

    @Size(max = 255, message = "Image url tối đa 255 ký tự")
    private String imageUrl;
}
