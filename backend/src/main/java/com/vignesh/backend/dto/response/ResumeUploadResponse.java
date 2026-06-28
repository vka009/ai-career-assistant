package com.vignesh.backend.dto.response;

import com.vignesh.backend.entity.ResumeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUploadResponse {

    private Long id;
    private String originalFileName;
    private ResumeStatus status;
    private Instant uploadedAt;

}
