package com.spring.marketplace.service;

import com.spring.marketplace.exception.ApplicationException;
import com.spring.marketplace.utils.enums.ErrorType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileStorageService {

    @Value("${app.download-upload.path}")
    private String basePath;

    @SneakyThrows
    public void uploadFile(MultipartFile file){
        try(InputStream inputStream = file.getInputStream()){
            Path path = Path.of(basePath);
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }

            Path fullPathToFile = Path.of(basePath + file.getOriginalFilename());
            Files.copy(inputStream,fullPathToFile, StandardCopyOption.REPLACE_EXISTING);
            log.info("File saved to {}", fullPathToFile);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            throw new ApplicationException(ErrorType.FAILED_TO_UPLOAD_FILE);
        }
    }

    @SneakyThrows
    public File downloadFile(String fileName) {
        File fileToDownload = new File(basePath + fileName);
        if(!fileToDownload.exists()){
            log.error("File does not exist");
            throw new ApplicationException(ErrorType.FAILED_TO_DOWNLOAD_FILE);
        }

        log.info("File download successfully");
        return fileToDownload;
    }

}
