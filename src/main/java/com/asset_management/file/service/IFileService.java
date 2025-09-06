package com.asset_management.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    public String uploadFile(MultipartFile file);
    public Resource downloadFile(String fileName);
}
