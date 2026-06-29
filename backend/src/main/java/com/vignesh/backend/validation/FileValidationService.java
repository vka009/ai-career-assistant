package com.vignesh.backend.validation;

import com.vignesh.backend.exception.FileValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileValidationService {
    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("pdf", "doc", "docx");

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public void validate(MultipartFile file) {
        validateFileNotEmpty(file);
        validateExtension(file);
        validateContentType(file);
        validateFileSize(file);
    }
    private void validateFileNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileValidationException("File must not be empty.");
        }
    }

    public void validateExtension(MultipartFile file) {

        String extension = getFileExtension(file);

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new FileValidationException("Only PDF, DOC and DOCX files are allowed.");
        }
    }

    public void validateContentType(MultipartFile file) {

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileValidationException("Invalid file type.");
        }
    }

    public void validateFileSize(MultipartFile file) {

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileValidationException("File size must not exceed 5 MB.");
        }
    }

    public String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            throw new FileValidationException("Invalid file name.");
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }

}