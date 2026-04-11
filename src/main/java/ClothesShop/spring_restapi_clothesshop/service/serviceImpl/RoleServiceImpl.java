package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleResponse;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Role;
import ClothesShop.spring_restapi_clothesshop.repository.RoleRepository;
import ClothesShop.spring_restapi_clothesshop.service.RoleService;
import ClothesShop.spring_restapi_clothesshop.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleCreateRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw AppException.duplicateResource("Role", "name", request.getName());
        }

        Role role = roleMapper.toEntity(request);

        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {
        return roleMapper.toResponse(findRoleByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> AppException.resourceNotFound("Role", "name", name));
        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toResponse);
    }

    @Override
    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {
        Role role = findRoleByIdOrThrow(id);

        if (request.getName() != null && !request.getName().equals(role.getName())
                && roleRepository.existsByName(request.getName())) {
            throw AppException.duplicateResource("Role", "name", request.getName());
        }

        if (request.getName() != null) {
            roleMapper.updateFromRequest(request, role);
        }

        Role updatedRole = roleRepository.save(role);
        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {
        findRoleByIdOrThrow(id);
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    private Role findRoleByIdOrThrow(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> AppException.resourceNotFound("Role", "id", id));
    }
}