package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionResponse;
import ClothesShop.spring_restapi_clothesshop.dto.permission.PermissionUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Permission toEntity(PermissionCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateFromRequest(PermissionUpdateRequest request, @MappingTarget Permission permission);

    PermissionResponse toResponse(Permission permission);
}