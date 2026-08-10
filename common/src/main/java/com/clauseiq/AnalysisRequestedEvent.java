package com.clauseiq;

/**
 * Sent by contract-service, read by ai-worker.
 *
 * Note that we only send the ids here, not the contract text.
 * Kafka messages have a 1 MB limit by default and a contract is much
 * bigger than that. The worker reads the actual text from the database.
 */
public class AnalysisRequestedEvent {

    private String jobId;
    private String tenantId;
    private String documentId;
    private String requestedBy;

    public AnalysisRequestedEvent() {
        // needed by Jackson
    }

    public AnalysisRequestedEvent(String jobId, String tenantId,
                                  String documentId, String requestedBy) {
        this.jobId = jobId;
        this.tenantId = tenantId;
        this.documentId = documentId;
        this.requestedBy = requestedBy;
    }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }
}