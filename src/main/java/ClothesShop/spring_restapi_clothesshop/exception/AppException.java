package ClothesShop.spring_restapi_clothesshop.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }

    public static AppException resourceNotFound(String resourceName, String fieldName, Object fieldValue) {
        return new AppException(
                ErrorCode.RESOURCE_NOT_FOUND,
                String.format("Không tìm thấy %s với %s = '%s'", resourceName, fieldName, fieldValue));
    }

    public static AppException duplicateResource(String resourceName, String fieldName, Object fieldValue) {
        return new AppException(
                ErrorCode.RESOURCE_ALREADY_EXISTS,
                String.format("%s đã tồn tại với %s = '%s'", resourceName, fieldName, fieldValue));
    }

    public static AppException invalidToken(String message) {
        return new AppException(ErrorCode.AUTH_INVALID_TOKEN, message);
    }

    public static AppException insufficientStock(String message) {
        return new AppException(ErrorCode.INSUFFICIENT_STOCK, message);
    }

    public static AppException fileUploadFailed(String message) {
        return new AppException(ErrorCode.FILE_UPLOAD_FAILED, message);
    }
}