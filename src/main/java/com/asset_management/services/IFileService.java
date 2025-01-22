package com.asset_management.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    public String uploadFile(MultipartFile file);
    public Resource downloadFile(String fileName);
}
