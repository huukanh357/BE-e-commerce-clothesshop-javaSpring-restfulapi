package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionResponse;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import ClothesShop.spring_restapi_clothesshop.service.PermissionService;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {

    PermissionService permissionService;
@GetMapping("/api/permissions")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getPermissions(
            @RequestParam(required = false) PermissionMethodENUM method,
            @RequestParam(required = false) String module,
            Pageable pageable) {
        Page<PermissionResponse> page = permissionService.filterPermissions(method, module, pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(page);
        return ResponseEntity.ok(ApiResponse.success("Get permissions successfully", result));
    }

    @GetMapping("/api/permissions/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        PermissionResponse permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success("Get permission successfully", permission));
    }

    @GetMapping("/api/permissions/name/{name}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionByName(@PathVariable String name) {
        PermissionResponse permission = permissionService.getPermissionByName(name);
        return ResponseEntity.ok(ApiResponse.success("Get permission successfully", permission));
    }

    @PostMapping("/api/permissions")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid PermissionCreateRequest request) {
        PermissionResponse permission = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Permission created successfully", permission));
    }

    @PutMapping("/api/permissions/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid PermissionUpdateRequest request) {
        PermissionResponse permission = permissionService.updatePermission(id, request);
        return ResponseEntity.ok(ApiResponse.success("Permission updated successfully", permission));
    }

    @DeleteMapping("/api/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/permissions/check/name")
    public ResponseEntity<ApiResponse<Boolean>> checkPermissionNameExists(@RequestParam String name) {
        boolean exists = permissionService.existsByName(name);
        return ResponseEntity
                .ok(ApiResponse.success(exists ? "Permission name already exists" : "Permission name available",
                        exists));
    }
}
