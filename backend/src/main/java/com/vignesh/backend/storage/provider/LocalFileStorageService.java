package com.vignesh.backend.storage.provider;

import com.vignesh.backend.config.StorageProperties;
import com.vignesh.backend.entity.StorageProvider;
import com.vignesh.backend.exception.StorageException;
import com.vignesh.backend.storage.FileStorageService;
import com.vignesh.backend.storage.dto.StorageResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(LocalFileStorageService.class);

    private final StorageProperties storageProperties;

    @Override
    public StorageResult store(MultipartFile file) {

        try {

            log.info("Starting upload for file: {}", file.getOriginalFilename());

            Path uploadPath = Paths.get(storageProperties.getLocation());

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

            log.info("Uploading file: {}", file.getOriginalFilename());

            Files.copy(file.getInputStream(), destination);

            StorageResult storageResult= StorageResult.builder()
                    .storageProvider(StorageProvider.LOCAL)
                    .storageReference(destination.toString())
                    .storedFileName(storedFileName)
                    .build();

            log.info("File stored successfully. Original: {}, Stored: {}",
                    originalFileName,
                    storedFileName);

            return storageResult;

        } catch (IOException e) {
            log.error("Failed to store file: {}", file.getOriginalFilename(), e);
            throw new StorageException("Failed to create upload directory", e);
        }

    }
}