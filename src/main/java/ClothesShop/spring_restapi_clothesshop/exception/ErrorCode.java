package ClothesShop.spring_restapi_clothesshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // Success response codes returned when the request is processed normally.
    SUCCESS(1000, "Success", HttpStatus.OK),

    // Authentication and token-related errors for login, access token, and refresh
    // token flows.
    AUTH_INVALID_CREDENTIALS(1001, "Email hoặc mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    AUTH_INVALID_TOKEN(1002, "Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_MISSING(1003, "Refresh token không được cung cấp", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(1004, "Refresh token không hợp lệ", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_REVOKED(1005, "Refresh token đã bị thu hồi", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(1006, "Refresh token đã hết hạn", HttpStatus.UNAUTHORIZED),

    // Domain and business rule errors used across entity lookup, duplicate checks,
    // stock, and uploads.
    RESOURCE_NOT_FOUND(1100, "Không tìm thấy %s với %s = '%s'", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(1101, "%s đã tồn tại với %s = '%s'", HttpStatus.CONFLICT),
    FILE_UPLOAD_FAILED(1102, "Tải file lên thất bại", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK(1103, "Số lượng tồn kho không đủ", HttpStatus.BAD_REQUEST),
    PRODUCT_OUT_OF_STOCK(1104, "Sản phẩm '%s' (size: %s, màu: %s) đã hết hàng", HttpStatus.BAD_REQUEST),
    STOCK_EXCEEDS_AVAILABLE(1105, "Số lượng vượt quá tồn kho. Tồn kho hiện tại: %d", HttpStatus.BAD_REQUEST),
    FILE_STORAGE_DIRECTORY_CREATE_FAILED(1106, "Không thể tạo thư mục lưu trữ: %s", HttpStatus.BAD_REQUEST),
    FILE_STORAGE_SAVE_FAILED(1107, "Không thể lưu file: %s", HttpStatus.BAD_REQUEST),
    FILE_REQUIRED(1108, "Không có file được cung cấp", HttpStatus.BAD_REQUEST),
    UPLOAD_FOLDER_REQUIRED(1109, "Folder không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_UPLOAD_FOLDER(1110, "Thư mục không hợp lệ. Chỉ chấp nhận: %s", HttpStatus.BAD_REQUEST),
    FILE_NAME_REQUIRED(1111, "Tên file không được để trống", HttpStatus.BAD_REQUEST),
    FILE_EXTENSION_NOT_ALLOWED(1112, "Định dạng file không được phép. Chỉ chấp nhận: %s", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED(1113, "Kích thước file vượt quá %d MB", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URL_DATA(1114, "Dữ liệu image_url không hợp lệ", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_SAVE_FAILED(1115, "Không thể lưu danh sách ảnh sản phẩm", HttpStatus.BAD_REQUEST),

    // Validation errors for user profile, account registration, and role assignment
    // input.
    USERNAME_REQUIRED(2001, "Username không được để trống", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2002, "Username phải từ 3 đến 50 ký tự", HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(2003, "Email không được để trống", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(2004, "Email không đúng định dạng", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(2005, "Password không được để trống", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(2006, "Password phải từ 6 đến 255 ký tự", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(2007, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    FULL_NAME_TOO_LONG(2008, "Full name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    ADDRESS_TOO_LONG(2009, "Address tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    CITY_TOO_LONG(2010, "City tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    AVATAR_TOO_LONG(2011, "Avatar tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    ROLE_ID_INVALID(2012, "Role Id phải lớn hơn 0", HttpStatus.BAD_REQUEST),

    // Validation errors for password update flows in self-service account
    // management.
    CURRENT_PASSWORD_REQUIRED(2101, "Mật khẩu hiện tại không được để trống", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_REQUIRED(2102, "Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_INVALID(2103, "Mật khẩu mới phải từ 6-50 ký tự", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_REQUIRED(2104, "Xác nhận mật khẩu không được để trống", HttpStatus.BAD_REQUEST),

    // Validation errors for shared metadata fields such as names, paths, and module
    // identifiers.
    NAME_REQUIRED(2201, "Name không được để trống", HttpStatus.BAD_REQUEST),
    NAME_TOO_LONG_100(2202, "Name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    NAME_TOO_LONG_255(2203, "Name tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    ROLE_NAME_TOO_LONG(2204, "Name tối đa 50 ký tự", HttpStatus.BAD_REQUEST),
    API_PATH_REQUIRED(2205, "Api path không được để trống", HttpStatus.BAD_REQUEST),
    API_PATH_TOO_LONG(2206, "Api path tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    MODULE_REQUIRED(2207, "Module không được để trống", HttpStatus.BAD_REQUEST),
    MODULE_TOO_LONG(2208, "Module tối đa 100 ký tự", HttpStatus.BAD_REQUEST),

    // Validation errors for identifiers, quantities, payment values, and order/cart
    // request payloads.
    USER_ID_REQUIRED(2301, "User id không được để trống", HttpStatus.BAD_REQUEST),
    USER_ID_INVALID(2302, "User id phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    CART_ID_REQUIRED(2303, "Cart id không được để trống", HttpStatus.BAD_REQUEST),
    CART_ID_INVALID(2304, "Cart id phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_REQUIRED(2305, "Product id không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_INVALID(2306, "Product id phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_ID_REQUIRED(2307, "Product detail id không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_ID_INVALID(2308, "Product detail id phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    ORDER_ID_REQUIRED(2309, "Order id không được để trống", HttpStatus.BAD_REQUEST),
    ORDER_ID_INVALID(2310, "Order id phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    QUANTITY_REQUIRED(2311, "Quantity không được để trống", HttpStatus.BAD_REQUEST),
    QUANTITY_INVALID(2312, "Quantity phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    QUANTITY_NON_NEGATIVE(2313, "quantity phải >= 0", HttpStatus.BAD_REQUEST),
    QUANTITY_MIN_ONE(2314, "quantity phải >= 1", HttpStatus.BAD_REQUEST),
    QUANTITY_TOO_LARGE(2315, "quantity không được vượt quá 99", HttpStatus.BAD_REQUEST),
    AMOUNT_REQUIRED(2316, "Amount không được để trống", HttpStatus.BAD_REQUEST),
    METHOD_REQUIRED(2317, "Method không được để trống", HttpStatus.BAD_REQUEST),
    TOTAL_AMOUNT_REQUIRED(2318, "Total amount không được để trống", HttpStatus.BAD_REQUEST),
    SHIPPING_INFO_REQUIRED(2319, "Shipping info không được để trống", HttpStatus.BAD_REQUEST),
    PRICE_REQUIRED(2320, "Price không được để trống", HttpStatus.BAD_REQUEST),

    // Validation errors for product detail and catalog data such as size, color,
    // stock, and category.
    SIZE_REQUIRED(2401, "Size không được để trống", HttpStatus.BAD_REQUEST),
    SIZE_TOO_LONG(2402, "Size tối đa 20 ký tự", HttpStatus.BAD_REQUEST),
    COLOR_TOO_LONG(2403, "Color tối đa 50 ký tự", HttpStatus.BAD_REQUEST),
    STOCK_QUANTITY_REQUIRED(2404, "Stock quantity không được để trống", HttpStatus.BAD_REQUEST),
    STOCK_QUANTITY_INVALID(2405, "Stock quantity không được âm", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_REQUIRED(2406, "Price không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_STOCK_REQUIRED(2407, "Stock quantity không được để trống", HttpStatus.BAD_REQUEST),
    PRODUCT_STOCK_INVALID(2408, "Stock quantity không được âm", HttpStatus.BAD_REQUEST),
    IMAGE_URL_TOO_LONG(2409, "Image url tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_REQUIRED(2410, "Category name không được để trống", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_TOO_LONG(2411, "Category name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),

    // Validation errors for shipping recipient information in checkout and order
    // creation.
    RECEIVER_NAME_REQUIRED(2501, "Receiver name không được để trống", HttpStatus.BAD_REQUEST),
    RECEIVER_NAME_TOO_LONG(2502, "Receiver name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    RECEIVER_PHONE_REQUIRED(2503, "Receiver phone không được để trống", HttpStatus.BAD_REQUEST),
    RECEIVER_PHONE_TOO_LONG(2504, "Receiver phone tối đa 20 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_ADDRESS_REQUIRED(2505, "Shipping address không được để trống", HttpStatus.BAD_REQUEST),
    SHIPPING_ADDRESS_TOO_LONG(2506, "Shipping address tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_CITY_REQUIRED(2507, "Shipping city không được để trống", HttpStatus.BAD_REQUEST),
    SHIPPING_CITY_TOO_LONG(2508, "Shipping city tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_NOTE_TOO_LONG(2509, "Shipping note tối đa 500 ký tự", HttpStatus.BAD_REQUEST),

    // Framework and infrastructure-level errors handled centrally by the global
    // exception layer.
    UNCATEGORIZED_EXCEPTION(9999, "Đã xảy ra lỗi hệ thống, vui lòng thử lại sau", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9000, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(9001, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_BODY(9002, "Request body không hợp lệ hoặc bị thiếu", HttpStatus.BAD_REQUEST),
    INVALID_PATH_VARIABLE(9003, "Tham số truyền vào không hợp lệ", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED(9004, "Phương thức HTTP không được hỗ trợ cho endpoint này", HttpStatus.METHOD_NOT_ALLOWED),
    ENDPOINT_NOT_FOUND(9005, "Không tìm thấy endpoint", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(9006, "Bạn không có quyền truy cập tài nguyên này", HttpStatus.FORBIDDEN),
    RATE_LIMIT_EXCEEDED(9007, "Bạn gọi API quá nhanh. Vui lòng thử lại sau.", HttpStatus.TOO_MANY_REQUESTS);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    public String format(Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        return String.format(message, args);
    }
}