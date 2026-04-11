package ClothesShop.spring_restapi_clothesshop.dto.role;

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
public class RoleCreateRequest {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(max = 50, message = "ROLE_NAME_TOO_LONG")
    private String name;
}