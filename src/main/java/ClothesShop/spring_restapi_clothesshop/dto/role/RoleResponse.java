package ClothesShop.spring_restapi_clothesshop.dto.role;

import ClothesShop.spring_restapi_clothesshop.model.Role;

public class RoleResponse {

    private Long id;
    private String name;

    public static RoleResponse fromEntity(Role role) {
        RoleResponse dto = new RoleResponse();
        dto.id = role.getId();
        dto.name = role.getName();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
