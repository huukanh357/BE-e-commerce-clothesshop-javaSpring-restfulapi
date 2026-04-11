package ClothesShop.spring_restapi_clothesshop.auth.dto;

import ClothesShop.spring_restapi_clothesshop.model.User;
import java.time.Instant;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        String phone,
        String fullName,
        String address,
        String city,
        String avatar,
        String role,
        Instant createdAt) {

    public static RegisterResponse fromEntity(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getFullName(),
                user.getAddress(),
                user.getCity(),
                user.getAvatar(),
                user.getRole() != null ? user.getRole().getName() : null,
                user.getCreatedAt());
    }
}