package com.vignesh.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String storedFileName;

    @Column(nullable = false)
    private String fileExtension;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResumeStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageProvider storageProvider;

    @Column(nullable = false)
    private String storageReference;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Instant uploadedAt;

    private Instant lastModifiedAt;

}