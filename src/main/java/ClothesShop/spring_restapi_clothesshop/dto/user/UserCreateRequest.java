package ClothesShop.spring_restapi_clothesshop.dto.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để tạo một User mới.
 * Sử dụng khi client gửi request tạo người dùng mới (đăng ký).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @NotBlank(message = "USERNAME_REQUIRED")
    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "PASSWORD_REQUIRED")
    @Size(min = 6, max = 255, message = "PASSWORD_INVALID")
    private String password;

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
}