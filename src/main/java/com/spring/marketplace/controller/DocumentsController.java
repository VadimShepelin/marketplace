package com.spring.marketplace.controller;

import com.spring.marketplace.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentsController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<String>> getReportFilesName(){
        return ResponseEntity.ok().body(reportService.getReportFilesName());
    }
}
