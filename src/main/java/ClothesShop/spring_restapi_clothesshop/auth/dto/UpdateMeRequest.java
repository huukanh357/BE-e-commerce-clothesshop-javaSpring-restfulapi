package ClothesShop.spring_restapi_clothesshop.auth.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMeRequest(
                @Pattern(regexp = "^(|[0-9+\\-\\s]{8,20})$", message = "PHONE_INVALID") String phone,

                @Size(max = 100, message = "FULL_NAME_TOO_LONG") String fullName,

                @Size(max = 255, message = "ADDRESS_TOO_LONG") String address,

                @Size(max = 100, message = "CITY_TOO_LONG") String city,

                @Size(max = 255, message = "AVATAR_TOO_LONG") String avatar) {
}