package com.ingestion_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/v1/ingestion")
public class IngestionController {

    @PostMapping("/playbook")
    public ResponseEntity<String> uploadPlaybook(@RequestHeader("X-Tenant-Id") String tenantId, @RequestParam("file") MultipartFile file) {
        // Logic to handle playbook upload

        return ResponseEntity.ok("Playbook uploaded successfully: " + tenantId);
    }
}
