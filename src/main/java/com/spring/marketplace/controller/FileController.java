package com.spring.marketplace.controller;

import com.spring.marketplace.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileStorageService fileService;

    @PostMapping("/save")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
        fileService.uploadFile(file);
        return ResponseEntity.ok("File was saved successfully");
    }
}
