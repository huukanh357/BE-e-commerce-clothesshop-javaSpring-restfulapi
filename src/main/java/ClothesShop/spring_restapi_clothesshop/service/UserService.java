package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.user.UserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface cho User.
 * Định nghĩa các business logic methods.
 */
public interface UserService {

    /**
     * Tạo user mới
     */
    UserResponse createUser(UserCreateRequest request);

    /**
     * Lấy user theo ID
     */
    UserResponse getUserById(Long id);

    /**
     * Lấy user theo username
     */
    UserResponse getUserByUsername(String username);

    /**
     * Lấy user theo email
     */
    UserResponse getUserByEmail(String email);

    /**
     * Lấy danh sách user có phân trang
     */
    Page<UserResponse> getAllUsers(Pageable pageable);

    /**
     * Cập nhật thông tin user
     */
    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse uploadAvatar(Long id, MultipartFile file);

    /**
     * Xóa user theo ID
     */
    void deleteUser(Long id);

    /**
     * Kiểm tra username đã tồn tại chưa
     */
    boolean existsByUsername(String username);

    /**
     * Kiểm tra email đã tồn tại chưa
     */
    boolean existsByEmail(String email);
}
