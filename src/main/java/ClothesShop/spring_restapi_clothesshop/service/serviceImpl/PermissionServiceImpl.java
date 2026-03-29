package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionResponse;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.exception.DuplicateResourceException;
import ClothesShop.spring_restapi_clothesshop.exception.ResourceNotFoundException;
import ClothesShop.spring_restapi_clothesshop.model.Permission;
import ClothesShop.spring_restapi_clothesshop.model.ENUM.PermissionMethodENUM;
import ClothesShop.spring_restapi_clothesshop.repository.PermissionRepository;
import ClothesShop.spring_restapi_clothesshop.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionResponse createPermission(PermissionCreateRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission", "name", request.getName());
        }

        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setApiPath(request.getApiPath());
        permission.setMethod(request.getMethod());
        permission.setModule(request.getModule());

        Permission savedPermission = permissionRepository.save(permission);
        return PermissionResponse.fromEntity(savedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long id) {
        return PermissionResponse.fromEntity(findPermissionByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionByName(String name) {
        Permission permission = permissionRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", name));
        return PermissionResponse.fromEntity(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponse> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(PermissionResponse::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermissionResponse> filterPermissions(PermissionMethodENUM method, String module, Pageable pageable) {
        if (method != null && module != null && !module.isBlank()) {
            return permissionRepository.findByMethodAndModule(method, module, pageable)
                    .map(PermissionResponse::fromEntity);
        }
        if (method != null) {
            return permissionRepository.findByMethod(method, pageable).map(PermissionResponse::fromEntity);
        }
        if (module != null && !module.isBlank()) {
            return permissionRepository.findByModule(module, pageable).map(PermissionResponse::fromEntity);
        }
        return permissionRepository.findAll(pageable).map(PermissionResponse::fromEntity);
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionUpdateRequest request) {
        Permission permission = findPermissionByIdOrThrow(id);

        if (request.getName() != null && !request.getName().equals(permission.getName())
                && permissionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Permission", "name", request.getName());
        }

        if (request.getName() != null) {
            permission.setName(request.getName());
        }
        if (request.getApiPath() != null) {
            permission.setApiPath(request.getApiPath());
        }
        if (request.getMethod() != null) {
            permission.setMethod(request.getMethod());
        }
        if (request.getModule() != null) {
            permission.setModule(request.getModule());
        }

        Permission updatedPermission = permissionRepository.save(permission);
        return PermissionResponse.fromEntity(updatedPermission);
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
