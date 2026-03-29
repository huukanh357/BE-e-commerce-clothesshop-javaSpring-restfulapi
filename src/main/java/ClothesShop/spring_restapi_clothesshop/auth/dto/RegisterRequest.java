package ClothesShop.spring_restapi_clothesshop.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
                @NotBlank(message = "Username không được để trống") @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự") String username,

                @NotBlank(message = "Email không được để trống") @Email(message = "Email không đúng định dạng") String email,

                @NotBlank(message = "Password không được để trống") @Size(min = 6, max = 255, message = "Password phải từ 6 đến 255 ký tự") String password,

                @Pattern(regexp = "^(|[0-9+\\-\\s]{8,20})$", message = "Số điện thoại không hợp lệ") String phone,

                @Size(max = 100, message = "Full name tối đa 100 ký tự") String fullName,

                @Size(max = 255, message = "Address tối đa 255 ký tự") String address,

                @Size(max = 100, message = "City tối đa 100 ký tự") String city,

                @Size(max = 255, message = "Avatar tối đa 255 ký tự") String avatar) {
}
