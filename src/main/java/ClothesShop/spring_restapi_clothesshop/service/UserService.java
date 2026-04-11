package ClothesShop.spring_restapi_clothesshop.service;

import ClothesShop.spring_restapi_clothesshop.dto.user.UserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    UserResponse getUserByEmail(String email);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse uploadAvatar(Long id, MultipartFile file);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}