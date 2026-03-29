package ClothesShop.spring_restapi_clothesshop.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request DTO để cập nhật thông tin User.
 * Sử dụng khi client gửi request cập nhật thông tin người dùng.
 * Lưu ý: email, username, password không thể cập nhật (nếu cần cập nhật cần
 * endpoint riêng)
 */
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

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String phone, String fullName, String address, String city, String avatar, Long roleId) {
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.avatar = avatar;
        this.roleId = roleId;
    }

    // ========== GETTERS ==========

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

    public Long getRoleId() {
        return roleId;
    }

    // ========== SETTERS ==========

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

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
