package com.vignesh.backend.controller;

import com.vignesh.backend.dto.response.ResumeUploadResponse;
import com.vignesh.backend.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeUploadResponse> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {

        return ResponseEntity.ok(resumeService.uploadResume(file, userId));
    }
}