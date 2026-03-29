package ClothesShop.spring_restapi_clothesshop.controller;

import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleResponse;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/api/roles")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllRoles(Pageable pageable) {
        Page<RoleResponse> page = roleService.getAllRoles(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get all roles successfully", result));
    }

    @GetMapping("/api/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success("Get role successfully", role));
    }

    @GetMapping("/api/roles/name/{name}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable String name) {
        RoleResponse role = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponse.success("Get role successfully", role));
    }

    @PostMapping("/api/roles")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody @Valid RoleCreateRequest request) {
        RoleResponse role = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Role created successfully", role));
    }

    @PutMapping("/api/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid RoleUpdateRequest request) {
        RoleResponse role = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", role));
    }

    @DeleteMapping("/api/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/roles/check/name")
    public ResponseEntity<ApiResponse<Boolean>> checkRoleNameExists(@RequestParam String name) {
        boolean exists = roleService.existsByName(name);
        return ResponseEntity
                .ok(ApiResponse.success(exists ? "Role name already exists" : "Role name available", exists));
    }
}
