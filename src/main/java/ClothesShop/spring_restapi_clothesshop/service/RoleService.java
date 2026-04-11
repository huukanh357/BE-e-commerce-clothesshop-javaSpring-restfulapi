package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.role.RoleCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleResponse;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleResponse createRole(RoleCreateRequest request);

    RoleResponse getRoleById(Long id);

    RoleResponse getRoleByName(String name);

    Page<RoleResponse> getAllRoles(Pageable pageable);

    RoleResponse updateRole(Long id, RoleUpdateRequest request);

    void deleteRole(Long id);

    boolean existsByName(String name);
}