package com.ingestion_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/v1/ingestion")
public class IngestionController {

    @PostMapping("/playbook")
    public ResponseEntity<String> uploadPlaybook(@RequestParam("file") MultipartFile file) {
        // Logic to handle playbook upload
        return ResponseEntity.ok("Playbook uploaded successfully");
    }
}
