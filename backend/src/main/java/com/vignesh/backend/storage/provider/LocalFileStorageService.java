package com.vignesh.backend.storage.provider;

import com.vignesh.backend.entity.StorageProvider;
import com.vignesh.backend.storage.FileStorageService;
import com.vignesh.backend.storage.dto.StorageResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${app.storage.location}")
    private String storageLocation;

    @Override
    public StorageResult store(MultipartFile file) {

        try {

            Path uploadPath = Paths.get(storageLocation);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String storedFileName = UUID.randomUUID() + extension;

            Path destination = uploadPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), destination);

            return StorageResult.builder()
                    .storageProvider(StorageProvider.LOCAL)
                    .storageReference(destination.toString())
                    .storedFileName(storedFileName)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }

    }
}