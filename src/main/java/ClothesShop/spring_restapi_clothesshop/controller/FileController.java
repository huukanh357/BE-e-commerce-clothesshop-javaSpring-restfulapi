package ClothesShop.spring_restapi_clothesshop.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ClothesShop.spring_restapi_clothesshop.dto.ApiResponse;
import ClothesShop.spring_restapi_clothesshop.dto.file.FileUploadResponse;
import ClothesShop.spring_restapi_clothesshop.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileController {

    FileService fileService;
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileUploadResponse>> upload(
            @RequestParam MultipartFile file,
            @RequestParam String folder) {
        FileUploadResponse response = fileService.upload(file, folder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("File uploaded", response));
    }
}
