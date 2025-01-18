package com.spring.marketplace.controller;

import com.spring.marketplace.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileStorageService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
        fileService.uploadFile(file);
        return ResponseEntity.ok("File was upload successfully");
    }

    @GetMapping("/download")
    @SneakyThrows
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        File fileToDownload = fileService.downloadFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToDownload.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileToDownload.length())
                .body(new InputStreamResource(Files.newInputStream(Path.of(fileToDownload.getPath()))));
    }
}
