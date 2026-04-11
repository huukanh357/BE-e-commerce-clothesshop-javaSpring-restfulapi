package ClothesShop.spring_restapi_clothesshop.exception;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(AppException.class)
        public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
                ErrorCode errorCode = ex.getErrorCode();
                log.warn("AppException [{}]: {}", errorCode.name(), ex.getMessage());
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiResponse.error(errorCode, ex.getMessage()));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
                log.warn("BadCredentialsException: {}", ex.getMessage());
                return ResponseEntity.status(ErrorCode.AUTH_INVALID_CREDENTIALS.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.AUTH_INVALID_CREDENTIALS));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
                log.warn("IllegalArgumentException: {}", ex.getMessage());
                return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.INVALID_REQUEST, ex.getMessage()));
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
                log.warn("AccessDeniedException: {}", ex.getMessage());
                return ResponseEntity.status(ErrorCode.ACCESS_DENIED.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.ACCESS_DENIED));
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
                String messageKey = ex.getConstraintViolations().stream()
                                .findFirst()
                                .map(ConstraintViolation::getMessage)
                                .orElse(ErrorCode.INVALID_KEY.name());
                ErrorCode errorCode = resolveValidationErrorCode(messageKey);
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiResponse.error(errorCode));
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
                log.warn("MethodArgumentTypeMismatchException: tham số '{}' nhận giá trị '{}'không hợp lệ",
                                ex.getName(), ex.getValue());
                String message = String.format("Tham số '%s' có giá trị '%s' không hợp lệ, kiểu dữ liệu yêu cầu: %s",
                                ex.getName(), ex.getValue(),
                                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "không xác định");
                return ResponseEntity.status(ErrorCode.INVALID_PATH_VARIABLE.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.INVALID_PATH_VARIABLE, message));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiResponse<Void>> handleNotReadable(HttpMessageNotReadableException ex) {
                log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
                return ResponseEntity.status(ErrorCode.INVALID_REQUEST_BODY.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.INVALID_REQUEST_BODY));
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
                log.warn("HttpRequestMethodNotSupportedException: method '{}' không được hỗ trợ", ex.getMethod());
                String message = String.format("Phương thức HTTP '%s' không được hỗ trợ cho endpoint này",
                                ex.getMethod());
                return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.METHOD_NOT_ALLOWED, message));
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleNoHandlerFound(NoHandlerFoundException ex) {
                log.warn("NoHandlerFoundException: không tìm thấy endpoint '{}' '{}'",
                                ex.getHttpMethod(), ex.getRequestURL());
                String message = String.format("Không tìm thấy endpoint '%s %s'",
                                ex.getHttpMethod(), ex.getRequestURL());
                return ResponseEntity.status(ErrorCode.ENDPOINT_NOT_FOUND.getStatusCode())
                                .body(ApiResponse.error(ErrorCode.ENDPOINT_NOT_FOUND, message));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Void>> handleValidationException(
                        MethodArgumentNotValidException ex) {
                String messageKey = ex.getBindingResult().getFieldErrors().stream()
                                .findFirst()
                                .map(error -> error.getDefaultMessage())
                                .orElse(ErrorCode.INVALID_KEY.name());
                ErrorCode errorCode = resolveValidationErrorCode(messageKey);
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiResponse.error(errorCode));
        }

        @ExceptionHandler(HandlerMethodValidationException.class)
        public ResponseEntity<ApiResponse<Void>> handleHandlerMethodValidationException(
                        HandlerMethodValidationException ex) {
                String messageKey = ErrorCode.INVALID_KEY.name();
                if (ex.getAllErrors() != null) {
                        messageKey = ex.getAllErrors().stream()
                                        .map(error -> error.getDefaultMessage())
                                        .filter(message -> message != null && !message.isBlank())
                                        .findFirst()
                                        .orElse(ErrorCode.INVALID_KEY.name());
                }
                ErrorCode errorCode = resolveValidationErrorCode(messageKey);
                return ResponseEntity.status(errorCode.getStatusCode())
                                .body(ApiResponse.error(errorCode));
        }

        private ErrorCode resolveValidationErrorCode(String messageKey) {
                return Optional.ofNullable(messageKey)
                                .map(String::trim)
                                .filter(key -> !key.isBlank())
                                .map(key -> {
                                        try {
                                                return ErrorCode.valueOf(key);
                                        } catch (IllegalArgumentException ignored) {
                                                return null;
                                        }
                                })
                                .orElse(ErrorCode.INVALID_KEY);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
                log.error("Unexpected exception: {}", ex.getMessage(), ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiResponse.error(ErrorCode.UNCATEGORIZED_EXCEPTION));
        }
}