package com.vignesh.backend.storage;

import com.vignesh.backend.storage.dto.StorageResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    StorageResult store(MultipartFile file);

}