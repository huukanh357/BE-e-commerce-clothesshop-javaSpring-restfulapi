package ClothesShop.spring_restapi_clothesshop.dto.user;

import ClothesShop.spring_restapi_clothesshop.model.User;
import java.time.Instant;

/**
 * Response DTO cho User.
 * Sử dụng khi trả về thông tin người dùng cho client.
 */
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private String city;
    private String avatar;
    private Long roleId;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, String email, String phone, String fullName,
            String address, String city, String avatar, Long roleId,
            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.avatar = avatar;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Chuyển từ User Entity sang UserResponse DTO
     */
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getFullName(),
                user.getAddress(),
                user.getCity(),
                user.getAvatar(),
                user.getRole().getId(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    // ========== GETTERS ==========

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

    public Long getRoleId() {
        return roleId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    // ========== SETTERS ==========

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
