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

    public AppException(ErrorCode errorCode, Object... messageArgs) {
        super(errorCode.format(messageArgs));
        this.errorCode = errorCode;
    }
}