package ClothesShop.spring_restapi_clothesshop.dto.file;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileUploadResponse {

    private String fileName;
    private String folder;
    private String fileUrl;
    private long size;
    private Instant uploadedAt;
}