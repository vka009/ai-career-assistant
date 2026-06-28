package com.vignesh.backend.mapper;

import com.vignesh.backend.dto.response.ResumeUploadResponse;
import com.vignesh.backend.entity.Resume;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    ResumeUploadResponse toResponse(Resume resume);

}