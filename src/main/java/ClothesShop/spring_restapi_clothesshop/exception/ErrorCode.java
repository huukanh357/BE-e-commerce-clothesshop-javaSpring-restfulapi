package ClothesShop.spring_restapi_clothesshop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    SUCCESS(1000, "Success", HttpStatus.OK),

    UNCATEGORIZED_EXCEPTION(9999, "Đã xảy ra lỗi hệ thống, vui lòng thử lại sau", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9000, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(9001, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_BODY(9002, "Request body không hợp lệ hoặc bị thiếu", HttpStatus.BAD_REQUEST),
    INVALID_PATH_VARIABLE(9003, "Tham số truyền vào không hợp lệ", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED(9004, "Phương thức HTTP không được hỗ trợ cho endpoint này", HttpStatus.METHOD_NOT_ALLOWED),
    ENDPOINT_NOT_FOUND(9005, "Không tìm thấy endpoint", HttpStatus.NOT_FOUND),
    ACCESS_DENIED(9006, "Bạn không có quyền truy cập tài nguyên này", HttpStatus.FORBIDDEN),
    RATE_LIMIT_EXCEEDED(9007, "Bạn gọi API quá nhanh. Vui lòng thử lại sau.", HttpStatus.TOO_MANY_REQUESTS),

    AUTH_INVALID_CREDENTIALS(1001, "Email hoặc mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    AUTH_INVALID_TOKEN(1002, "Token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED),

    RESOURCE_NOT_FOUND(1100, "Không tìm thấy tài nguyên yêu cầu", HttpStatus.NOT_FOUND),
    RESOURCE_ALREADY_EXISTS(1101, "Tài nguyên đã tồn tại", HttpStatus.CONFLICT),
    FILE_UPLOAD_FAILED(1102, "Tải file lên thất bại", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK(1103, "Số lượng tồn kho không đủ", HttpStatus.BAD_REQUEST),

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

    CURRENT_PASSWORD_REQUIRED(2101, "Mật khẩu hiện tại không được để trống", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_REQUIRED(2102, "Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_INVALID(2103, "Mật khẩu mới phải từ 6-50 ký tự", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_REQUIRED(2104, "Xác nhận mật khẩu không được để trống", HttpStatus.BAD_REQUEST),

    NAME_REQUIRED(2201, "Name không được để trống", HttpStatus.BAD_REQUEST),
    NAME_TOO_LONG_100(2202, "Name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    NAME_TOO_LONG_255(2203, "Name tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    ROLE_NAME_TOO_LONG(2204, "Name tối đa 50 ký tự", HttpStatus.BAD_REQUEST),
    API_PATH_REQUIRED(2205, "Api path không được để trống", HttpStatus.BAD_REQUEST),
    API_PATH_TOO_LONG(2206, "Api path tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    MODULE_REQUIRED(2207, "Module không được để trống", HttpStatus.BAD_REQUEST),
    MODULE_TOO_LONG(2208, "Module tối đa 100 ký tự", HttpStatus.BAD_REQUEST),

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

    RECEIVER_NAME_REQUIRED(2501, "Receiver name không được để trống", HttpStatus.BAD_REQUEST),
    RECEIVER_NAME_TOO_LONG(2502, "Receiver name tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    RECEIVER_PHONE_REQUIRED(2503, "Receiver phone không được để trống", HttpStatus.BAD_REQUEST),
    RECEIVER_PHONE_TOO_LONG(2504, "Receiver phone tối đa 20 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_ADDRESS_REQUIRED(2505, "Shipping address không được để trống", HttpStatus.BAD_REQUEST),
    SHIPPING_ADDRESS_TOO_LONG(2506, "Shipping address tối đa 255 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_CITY_REQUIRED(2507, "Shipping city không được để trống", HttpStatus.BAD_REQUEST),
    SHIPPING_CITY_TOO_LONG(2508, "Shipping city tối đa 100 ký tự", HttpStatus.BAD_REQUEST),
    SHIPPING_NOTE_TOO_LONG(2509, "Shipping note tối đa 500 ký tự", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}