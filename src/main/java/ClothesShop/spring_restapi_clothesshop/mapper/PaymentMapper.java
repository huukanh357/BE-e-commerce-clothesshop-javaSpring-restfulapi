package ClothesShop.spring_restapi_clothesshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentResponse;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.payment.PaymentUserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Payment toEntity(PaymentCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Payment toEntity(PaymentUserCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(PaymentUpdateRequest request, @MappingTarget Payment payment);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    PaymentResponse toResponse(Payment payment);
}