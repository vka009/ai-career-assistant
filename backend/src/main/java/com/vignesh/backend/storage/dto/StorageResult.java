package com.vignesh.backend.storage.dto;

import com.vignesh.backend.entity.StorageProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageResult {

    private StorageProvider storageProvider;

    private String storageReference;

    private String storedFileName;

}