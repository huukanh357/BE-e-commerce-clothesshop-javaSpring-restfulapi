package ClothesShop.spring_restapi_clothesshop.dto.role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.Role;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    private Long id;
    private String name;

    public static RoleResponse fromEntity(Role role) {
        RoleResponse dto = new RoleResponse();
        dto.id = role.getId();
        dto.name = role.getName();
        return dto;
    }
}
