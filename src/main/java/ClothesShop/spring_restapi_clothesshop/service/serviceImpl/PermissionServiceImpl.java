package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionResponse;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import ClothesShop.spring_restapi_clothesshop.repository.PermissionRepository;
import ClothesShop.spring_restapi_clothesshop.service.PermissionService;
import ClothesShop.spring_restapi_clothesshop.mapper.PermissionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionCreateRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission", "name", request.getName());
        }

        Permission permission = permissionMapper.toEntity(request);

        Permission savedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(savedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long id) {
        return permissionMapper.toResponse(findPermissionByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));
        return permissionMapper.toResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponse> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(permissionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponse> filterPermissions(PermissionMethodENUM method, String module, Pageable pageable) {
        if (method != null && module != null && !module.isBlank()) {
            return permissionRepository.findByMethodAndModule(method, module, pageable)
                    .map(permissionMapper::toResponse);
        }
        if (method != null) {
            return permissionRepository.findByMethod(method, pageable).map(permissionMapper::toResponse);
        }
        if (module != null && !module.isBlank()) {
            return permissionRepository.findByModule(module, pageable).map(permissionMapper::toResponse);
        }
        return permissionRepository.findAll(pageable).map(permissionMapper::toResponse);
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionUpdateRequest request) {
        Permission permission = findPermissionByIdOrThrow(id);

        if (request.getName() != null && !request.getName().equals(permission.getName())
                && permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission", "name", request.getName());
        }

        permissionMapper.updateFromRequest(request, permission);

        Permission updatedPermission = permissionRepository.save(permission);
        return permissionMapper.toResponse(updatedPermission);
    }

    @Override
    public void deletePermission(Long id) {
        findPermissionByIdOrThrow(id);
        permissionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return permissionRepository.existsByName(name);
    }

    private Permission findPermissionByIdOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
    }
}
