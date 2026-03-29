package ClothesShop.spring_restapi_clothesshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để tạo một User mới.
 * Sử dụng khi client gửi request tạo người dùng mới (đăng ký).
 */
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

    public UserCreateRequest() {
    }

    public UserCreateRequest(String username, String email, String password, String phone,
            String fullName, String address, String city, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.avatar = avatar;
    }

    // ========== GETTERS ==========

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getAvatar() {
        return avatar;
    }

    // ========== SETTERS ==========

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
