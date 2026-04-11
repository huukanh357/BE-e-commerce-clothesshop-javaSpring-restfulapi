package ClothesShop.spring_restapi_clothesshop.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để cập nhật thông tin User.
 * Sử dụng khi client gửi request cập nhật thông tin người dùng.
 * Lưu ý: email, username, password không thể cập nhật (nếu cần cập nhật cần
 * endpoint riêng)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Pattern(regexp = "^(|[0-9+\\-\\s]{8,20})$", message = "PHONE_INVALID")
    private String phone;

    @Size(max = 100, message = "FULL_NAME_TOO_LONG")
    private String fullName;

    @Size(max = 255, message = "ADDRESS_TOO_LONG")
    private String address;

    @Size(max = 100, message = "CITY_TOO_LONG")
    private String city;

    @Size(max = 255, message = "AVATAR_TOO_LONG")
    private String avatar;

    @Positive(message = "ROLE_ID_INVALID")
    private Long roleId;
}