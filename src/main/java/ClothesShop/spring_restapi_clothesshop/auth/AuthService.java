package ClothesShop.spring_restapi_clothesshop.auth;

import ClothesShop.spring_restapi_clothesshop.auth.dto.ChangePasswordRequest;
import ClothesShop.spring_restapi_clothesshop.auth.dto.LoginRequest;
import ClothesShop.spring_restapi_clothesshop.auth.dto.LoginResponse;
import ClothesShop.spring_restapi_clothesshop.auth.dto.RegisterRequest;
import ClothesShop.spring_restapi_clothesshop.auth.dto.RegisterResponse;
import ClothesShop.spring_restapi_clothesshop.auth.dto.UpdateMeRequest;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    LoginResponse login(LoginRequest request, String deviceInfo, String ipAddress);

    RegisterResponse register(RegisterRequest request);

    LoginResponse refresh(String rawRefreshToken);

    void logout(String rawRefreshToken);

    UserResponse getMe(String email);

    UserResponse updateMe(String email, UpdateMeRequest request);

    UserResponse uploadMyAvatar(String email, MultipartFile file);

    void changePassword(String email, ChangePasswordRequest request);
}
