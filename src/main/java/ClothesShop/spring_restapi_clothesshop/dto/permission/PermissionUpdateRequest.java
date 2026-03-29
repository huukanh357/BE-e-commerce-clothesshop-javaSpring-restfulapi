package ClothesShop.spring_restapi_clothesshop.dto.permission;

import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import jakarta.validation.constraints.Size;

public class PermissionUpdateRequest {

    @Size(max = 100, message = "Name tối đa 100 ký tự")
    private String name;

    @Size(max = 255, message = "Api path tối đa 255 ký tự")
    private String apiPath;

    private PermissionMethodENUM method;

    @Size(max = 100, message = "Module tối đa 100 ký tự")
    private String module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public PermissionMethodENUM getMethod() {
        return method;
    }

    public void setMethod(PermissionMethodENUM method) {
        this.method = method;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
