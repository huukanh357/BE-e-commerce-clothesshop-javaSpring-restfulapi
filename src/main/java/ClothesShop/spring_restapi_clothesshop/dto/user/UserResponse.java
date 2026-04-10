package ClothesShop.spring_restapi_clothesshop.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ClothesShop.spring_restapi_clothesshop.model.User;
import java.time.Instant;

/**
 * Response DTO cho User.
 * Sử dụng khi trả về thông tin người dùng cho client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private String city;
    private String avatar;
    private Long roleId;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Chuyển từ User Entity sang UserResponse DTO
     */
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getFullName(),
                user.getAddress(),
                user.getCity(),
                user.getAvatar(),
                user.getRole().getId(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
