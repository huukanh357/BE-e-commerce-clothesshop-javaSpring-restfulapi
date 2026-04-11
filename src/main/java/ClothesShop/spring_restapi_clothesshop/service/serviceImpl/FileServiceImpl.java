package ClothesShop.spring_restapi_clothesshop.service.serviceImpl;

import ClothesShop.spring_restapi_clothesshop.exception.AppException;
import ClothesShop.spring_restapi_clothesshop.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.config.FileUploadProperties;
import ClothesShop.spring_restapi_clothesshop.dto.file.FileUploadResponse;
import ClothesShop.spring_restapi_clothesshop.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileServiceImpl implements FileService {

    FileUploadProperties uploadProperties;

    @Override
    public FileUploadResponse upload(MultipartFile file, String folder) {
        validateFile(file, folder);

        String originalName = file.getOriginalFilename();
        String sanitizedName = sanitizeFileName(originalName);
        String storedName = System.currentTimeMillis() + "_" + sanitizedName;

        Path targetDir = Path.of(uploadProperties.getBaseDir(), folder);
        try {
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_STORAGE_DIRECTORY_CREATE_FAILED, e.getMessage());
        }

        try {
            Path targetPath = targetDir.resolve(storedName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_STORAGE_SAVE_FAILED, e.getMessage());
        }

        String fileUrl = "/uploads/" + folder + "/" + storedName;
        return new FileUploadResponse(storedName, folder, fileUrl, file.getSize(), Instant.now());
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^A-Za-z0-9._\\-]", "_");
    }

    private void validateFile(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.FILE_REQUIRED);
        }

        if (folder == null || folder.isBlank()) {
            throw new AppException(ErrorCode.UPLOAD_FOLDER_REQUIRED);
        }

        String normalizedFolder = folder.trim().toLowerCase();
        if (!uploadProperties.getAllowedFolders().contains(normalizedFolder)) {
            String allowed = String.join(", ", uploadProperties.getAllowedFolders());
            throw new AppException(ErrorCode.INVALID_UPLOAD_FOLDER, allowed);
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new AppException(ErrorCode.FILE_NAME_REQUIRED);
        }

        String extension = getExtension(originalName).toLowerCase();
        if (!uploadProperties.getAllowedExtensions().contains(extension)) {
            String allowed = String.join(", ", uploadProperties.getAllowedExtensions());
            throw new AppException(ErrorCode.FILE_EXTENSION_NOT_ALLOWED, allowed);
        }

        if (file.getSize() > uploadProperties.getMaxFileSize()) {
            long maxMb = uploadProperties.getMaxFileSize() / (1024 * 1024);
            throw new AppException(ErrorCode.FILE_SIZE_EXCEEDED, maxMb);
        }
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
}
