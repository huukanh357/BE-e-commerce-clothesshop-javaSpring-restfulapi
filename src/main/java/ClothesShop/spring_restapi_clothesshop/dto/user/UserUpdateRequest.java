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

    @Positive(message = "Role Id phải lớn hơn 0")
    private Long roleId;
}
