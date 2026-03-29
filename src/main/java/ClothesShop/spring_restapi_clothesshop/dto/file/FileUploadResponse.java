package ClothesShop.spring_restapi_clothesshop.dto.file;

import java.time.Instant;

public class FileUploadResponse {

    private String fileName;
    private String folder;
    private String fileUrl;
    private long size;
    private Instant uploadedAt;

    public FileUploadResponse() {
    }

    public FileUploadResponse(String fileName, String folder, String fileUrl, long size, Instant uploadedAt) {
        this.fileName = fileName;
        this.folder = folder;
        this.fileUrl = fileUrl;
        this.size = size;
        this.uploadedAt = uploadedAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
