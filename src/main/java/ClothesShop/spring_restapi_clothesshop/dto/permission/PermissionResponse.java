package ClothesShop.spring_restapi_clothesshop.dto.permission;

import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApiPath() {
        return apiPath;
    }

    public PermissionMethodENUM getMethod() {
        return method;
    }

    public String getModule() {
        return module;
    }
}
