package ClothesShop.spring_restapi_clothesshop.dto.permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {

    private Long id;
    private String name;
    private String apiPath;
    private PermissionMethodENUM method;
    private String module;

    public static PermissionResponse fromEntity(Permission permission) {
        PermissionResponse dto = new PermissionResponse();
        dto.id = permission.getId();
        dto.name = permission.getName();
        dto.apiPath = permission.getApiPath();
        dto.method = permission.getMethod();
        dto.module = permission.getModule();
        return dto;
    }
}