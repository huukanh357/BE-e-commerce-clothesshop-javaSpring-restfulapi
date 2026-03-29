package ClothesShop.spring_restapi_clothesshop.exception;

public class FileUploadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileUploadException(String message) {
        super(message);
    }
}
