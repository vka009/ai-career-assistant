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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    private final FileStorageService fileStorageService;

    private final FileValidationService fileValidationService;

    private final ResumeMapper resumeMapper;

    public ResumeUploadResponse uploadResume(MultipartFile file, Long userId) {

        fileValidationService.validate(file);

        StorageResult storageResult = fileStorageService.store(file);

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
        return resumeMapper.toResponse(savedResume);

    }
}
