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

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, max = 255, message = "Password phải từ 6 đến 255 ký tự")
    private String password;

    @Pattern(regexp = "^(|[0-9+\\-\\s]{8,20})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 100, message = "Full name tối đa 100 ký tự")
    private String fullName;

    @Size(max = 255, message = "Address tối đa 255 ký tự")
    private String address;

    @Size(max = 100, message = "City tối đa 100 ký tự")
    private String city;

    @Size(max = 255, message = "Avatar tối đa 255 ký tự")
    private String avatar;
}
