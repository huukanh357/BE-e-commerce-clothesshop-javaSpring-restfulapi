package ClothesShop.spring_restapi_clothesshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryCreateRequest {

    @NotBlank(message = "Name không được để trống")
    @Size(max = 100, message = "Name tối đa 100 ký tự")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
