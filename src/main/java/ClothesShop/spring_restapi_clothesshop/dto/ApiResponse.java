package ClothesShop.spring_restapi_clothesshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper class cho t?t c? API responses.
 * ??m b?o format nh?t qu?n cho c? success v? error cases.
 *
 * @param <T> Ki?u d? li?u c?a field data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiResponse<T> {

    // ========== FIELDS ==========

    private int statusCode;
    private String message;

    // Ch? c? khi SUCCESS
    private T data;

    // Ch? c? khi ERROR
    private String error;

    // Ch? c? khi VALIDATION ERROR (nhi?u l?i)
    private List<String> details;

    // ========== CONSTRUCTORS ==========

    public ApiResponse() {
    }

    // Constructor cho Success
    private ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // Constructor cho Error
    private ApiResponse(int statusCode, String message, String error, List<String> details) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
        this.details = details;
    }

    // ========== SUCCESS METHODS ==========

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, "Created successfully", data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(201, message, data);
    }

    // ========== ERROR METHODS ==========

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(404, message, "Not Found", null);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message, "Bad Request", null);
    }

    public static <T> ApiResponse<T> badRequest(String message, List<String> details) {
        return new ApiResponse<>(400, message, "Bad Request", details);
    }

    public static <T> ApiResponse<T> conflict(String message) {
        return new ApiResponse<>(409, message, "Conflict", null);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(401, message, "Unauthorized", null);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(403, message, "Forbidden", null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, "Internal Server Error", null);
    }

    public static <T> ApiResponse<T> of(int statusCode, String message, T data) {
        return new ApiResponse<>(statusCode, message, data);
    }

    public static <T> ApiResponse<T> ofError(int statusCode, String message, String error) {
        return new ApiResponse<>(statusCode, message, error, null);
    }
}
