package ClothesShop.spring_restapi_clothesshop.auth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMeRequest(
        @Pattern(regexp = "^(|[0-9+\\-\\s]{8,20})$", message = "Số điện thoại không hợp lệ") String phone,

        @Size(max = 100, message = "Full name tối đa 100 ký tự") String fullName,

        @Size(max = 255, message = "Address tối đa 255 ký tự") String address,

        @Size(max = 100, message = "City tối đa 100 ký tự") String city,

        @Size(max = 255, message = "Avatar tối đa 255 ký tự") String avatar) {
}