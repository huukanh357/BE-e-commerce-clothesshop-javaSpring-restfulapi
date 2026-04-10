package ClothesShop.spring_restapi_clothesshop.dto.permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionUpdateRequest {

    @Size(max = 100, message = "Name tối đa 100 ký tự")
    private String name;

    @Size(max = 255, message = "Api path tối đa 255 ký tự")
    private String apiPath;

    private PermissionMethodENUM method;

    @Size(max = 100, message = "Module tối đa 100 ký tự")
    private String module;
}
