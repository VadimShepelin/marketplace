package com.spring.marketplace.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

    @GetMapping
    public List getDocumentsInfo(){
        return null;
    }
}
