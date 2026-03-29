package ClothesShop.spring_restapi_clothesshop.dto.role;

import jakarta.validation.constraints.Size;

public class RoleUpdateRequest {

    @Size(max = 50, message = "Name tối đa 50 ký tự")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
