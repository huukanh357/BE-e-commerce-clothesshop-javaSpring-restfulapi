package ClothesShop.spring_restapi_clothesshop.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleCreateRequest {

    @NotBlank(message = "Name không được để trống")
    @Size(max = 50, message = "Name tối đa 50 ký tự")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
