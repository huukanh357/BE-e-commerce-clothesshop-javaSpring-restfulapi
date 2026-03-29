package ClothesShop.spring_restapi_clothesshop.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để thay đổi mật khẩu người dùng.
 * Sử dụng khi user gửi request thay đổi mật khẩu của chính mình.
 */
public record ChangePasswordRequest(
        @NotBlank(message = "Mật khẩu hiện tại không được để trống") String currentPassword,

        @NotBlank(message = "Mật khẩu mới không được để trống") @Size(min = 6, max = 50, message = "Mật khẩu mới phải từ 6-50 ký tự") String newPassword,

        @NotBlank(message = "Xác nhận mật khẩu không được để trống") String confirmPassword) {
}
