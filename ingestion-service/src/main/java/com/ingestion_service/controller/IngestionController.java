package com.ingestion_service.controller;

import com.clauseiq.AppConstants;
import com.ingestion_service.dto.IngestResult;
import com.ingestion_service.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping("/playbook")
    public ResponseEntity<String> uploadPlaybook(@RequestHeader("X-Tenant-Id") String tenantId, @RequestParam("file") MultipartFile file) {
        // Logic to handle playbook upload

        try {
            IngestResult result = ingestionService.ingest(file, tenantId, "PLAYBOOK");
            return ResponseEntity.ok("Playbook uploaded successfully: " + tenantId + ", Document ID: " + result.getDocumentId() + ", Chunks: " + result.getChunks());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading playbook: " + e.getMessage());
        }
    }

    @PostMapping("/contract")
    public ResponseEntity<IngestResult> uploadContract(
            @RequestParam("file") MultipartFile file,
            @RequestHeader(AppConstants.HEADER_TENANT_ID) String tenantId)
            throws IOException {

        IngestResult result =
                ingestionService.ingest(file, tenantId, "CONTRACT");

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
