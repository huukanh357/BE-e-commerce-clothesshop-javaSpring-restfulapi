package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionResponse;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {

    PermissionResponse createPermission(PermissionCreateRequest request);

    PermissionResponse getPermissionById(Long id);

    PermissionResponse getPermissionByName(String name);

    Page<PermissionResponse> getAllPermissions(Pageable pageable);

    Page<PermissionResponse> filterPermissions(PermissionMethodENUM method, String module, Pageable pageable);

    PermissionResponse updatePermission(Long id, PermissionUpdateRequest request);

    void deletePermission(Long id);

    boolean existsByName(String name);
}
