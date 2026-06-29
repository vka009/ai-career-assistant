package com.vignesh.backend.service;

import com.vignesh.backend.dto.response.ResumeUploadResponse;
import com.vignesh.backend.entity.Resume;
import com.vignesh.backend.entity.ResumeStatus;
import com.vignesh.backend.mapper.ResumeMapper;
import com.vignesh.backend.repository.ResumeRepository;
import com.vignesh.backend.storage.FileStorageService;
import com.vignesh.backend.storage.dto.StorageResult;
import com.vignesh.backend.validation.FileValidationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    private final FileStorageService fileStorageService;

    private final FileValidationService fileValidationService;

    private static final Logger log = LoggerFactory.getLogger(ResumeService.class);

    private final ResumeMapper resumeMapper;

    public ResumeUploadResponse uploadResume(MultipartFile file, Long userId) {

        fileValidationService.validate(file);

        log.info("Resume validation completed successfully.");

        StorageResult storageResult = fileStorageService.store(file);

        log.info("Saving resume metadata for userId: {}", userId);
        
        Resume resume = Resume.builder()
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storageResult.getStoredFileName())
                .fileExtension(fileValidationService.getFileExtension(file))
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .status(ResumeStatus.UPLOADED)
                .storageProvider(storageResult.getStorageProvider())
                .storageReference(storageResult.getStorageReference())
                .userId(userId)
                .uploadedAt(Instant.now())
                .build();

       Resume savedResume= resumeRepository.save(resume);
        log.info("Resume uploaded successfully. Resume Id: {}", savedResume.getId());
        return resumeMapper.toResponse(savedResume);

    }
}
