package com.clauseiq;

import lombok.*;

/**
 * Sent by contract-service, read by ai-worker.
 *
 * Note that we only send the ids here, not the contract text.
 * Kafka messages have a 1 MB limit by default and a contract is much
 * bigger than that. The worker reads the actual text from the database.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisRequestedEvent {

    private String jobId;
    private String tenantId;
    private String documentId;
    private String requestedBy;

}