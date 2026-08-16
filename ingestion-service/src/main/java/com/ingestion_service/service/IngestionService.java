package com.ingestion_service.service;

import com.ingestion_service.dto.IngestResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngestionService {

    private final VectorStore vectorStore;

    public IngestResult ingest(MultipartFile file, String tenantId, String requestType) throws IOException {

        UUID documentId = UUID.randomUUID();

        Resource resource = new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);

        List<Document> pages = reader.read();

        TokenTextSplitter splitter = new TokenTextSplitter(
                700,
                350,
                20,
                2000,
                true
        );

        List<Document> chunks = splitter.split(pages);

        for (int i = 0; i < chunks.size(); i++){
            Document chunk = chunks.get(i);
            chunk.getMetadata().put("tenantId", tenantId);
            chunk.getMetadata().put("documentId", documentId.toString());
            chunk.getMetadata().put("requestType", requestType);
            chunk.getMetadata().put("chunkIndex", String.valueOf(i));
        }

        vectorStore.add(chunks);

        log.info("Saved document {} for tenant {}. Pages: {}, chunks: {}",
                documentId, tenantId, pages.size(), chunks.size());

        return new IngestResult(documentId.toString(), chunks.size());
    }


}
