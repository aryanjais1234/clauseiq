package com.clauseiq;

import lombok.*;

/**
 * Sent by ai-worker while it is working, read by contract-service.
 * This is what lets the UI show "clause 12 of 47".
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisProgressEvent {

    private String jobId;
    private String tenantId;
    private JobStatus status;
    private int processedClauses;
    private int totalClauses;
    private String errorMessage;

}