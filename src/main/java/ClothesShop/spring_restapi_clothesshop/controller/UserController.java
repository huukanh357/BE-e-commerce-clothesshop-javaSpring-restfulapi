package ClothesShop.spring_restapi_clothesshop.controller;

import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.ResultPaginationDTO;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserCreateRequest;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserResponse;
import ClothesShop.spring_restapi_clothesshop.dto.user.UserUpdateRequest;
import ClothesShop.spring_restapi_clothesshop.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<ApiResponse<ResultPaginationDTO>> getAllUsers(Pageable pageable) {
        Page<UserResponse> userPage = userService.getAllUsers(pageable);
        ResultPaginationDTO result = ResultPaginationDTO.fromPage(userPage);
        return ResponseEntity.ok(ApiResponse.success("Get all users successfully", result));
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("Get user successfully", user));
    }

    @GetMapping("/api/users/username/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse user = userService.getUserByUsername(username);
        return ResponseEntity.ok(ApiResponse.success("Get user successfully", user));
    }

    @GetMapping("/api/users/email/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByEmail(
            @PathVariable @Email(message = "Email không đúng định dạng") String email) {
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Get user successfully", user));
    }

    @PostMapping("/api/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse newUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("User created successfully", newUser));
    }

    @PostMapping(value = "/api/users/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UserResponse>> uploadAvatar(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestParam("file") MultipartFile file) {
        UserResponse updatedUser = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(ApiResponse.success("Avatar uploaded successfully", updatedUser));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id,
            @RequestBody @Valid UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Positive(message = "Id phải lớn hơn 0") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/users/check/username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameExists(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity
                .ok(ApiResponse.success(exists ? "Username already exists" : "Username available", exists));
    }

    @GetMapping("/api/users/check/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(
            @PathVariable @Email(message = "Email không đúng định dạng") String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(exists ? "Email already exists" : "Email available", exists));
    }
}
