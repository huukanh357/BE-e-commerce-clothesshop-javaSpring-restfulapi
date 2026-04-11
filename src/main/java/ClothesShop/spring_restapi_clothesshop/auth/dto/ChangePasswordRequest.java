package ClothesShop.spring_restapi_clothesshop.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để thay đổi mật khẩu người dùng.
 * Sử dụng khi user gửi request thay đổi mật khẩu của chính mình.
 */
public record ChangePasswordRequest(
                @NotBlank(message = "CURRENT_PASSWORD_REQUIRED") String currentPassword,

                @NotBlank(message = "NEW_PASSWORD_REQUIRED") @Size(min = 6, max = 50, message = "NEW_PASSWORD_INVALID") String newPassword,

                @NotBlank(message = "CONFIRM_PASSWORD_REQUIRED") String confirmPassword) {
}