package com.asset_management.services.impl;

import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.services.IFileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class FileService implements IFileService {
    @Value("${application.file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Path desPath = Paths.get(uploadDir);
            if (Files.notExists(desPath)) {
                Files.createDirectories(desPath);
            }

            long now = Instant.now().toEpochMilli();
            String fileName = now + "_" + file.getOriginalFilename();

            Path desFilePath = desPath.resolve(fileName);
            Files.copy(file.getInputStream(), desFilePath);

            return fileName;
        } catch (Exception e) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "File upload failed");
        }
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "File not found. Please verify the file path or name and try again.");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "File download failed.");
        }
    }
}
