package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleResponse;
import ClothesShop.spring_restapi_clothesshop.dto.role.RoleUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateFromRequest(RoleUpdateRequest request, @MappingTarget Role role);

    RoleResponse toResponse(Role role);
}
