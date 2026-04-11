package ClothesShop.spring_restapi_clothesshop.dto;

import ClothesShop.spring_restapi_clothesshop.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private int code;
    private String message;
    private T result;

    public ApiResponse() {
    }

    private ApiResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), "Created successfully", data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode(), message, null);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(ErrorCode.AUTH_INVALID_TOKEN, message);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return error(ErrorCode.ACCESS_DENIED, message);
    }

    public static <T> ApiResponse<T> ofError(ErrorCode errorCode) {
        return error(errorCode);
    }

    public static <T> ApiResponse<T> ofError(ErrorCode errorCode, String message) {
        return error(errorCode, message);
    }
}