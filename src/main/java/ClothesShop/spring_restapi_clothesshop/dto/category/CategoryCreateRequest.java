package ClothesShop.spring_restapi_clothesshop.dto.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateRequest {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = 100, message = "NAME_TOO_LONG_100")
    private String name;
}