package ClothesShop.spring_restapi_clothesshop.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilterRequest {

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private List<String> categoryNames;

    private List<String> sizes;
}
