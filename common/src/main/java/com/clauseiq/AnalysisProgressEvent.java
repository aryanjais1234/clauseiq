package com.clauseiq;

/**
 * Sent by ai-worker while it is working, read by contract-service.
 * This is what lets the UI show "clause 12 of 47".
 */
public class AnalysisProgressEvent {

    private String jobId;
    private String tenantId;
    private JobStatus status;
    private int processedClauses;
    private int totalClauses;
    private String errorMessage;

    public AnalysisProgressEvent() {
    }

    public AnalysisProgressEvent(String jobId, String tenantId, JobStatus status,
                                 int processedClauses, int totalClauses) {
        this.jobId = jobId;
        this.tenantId = tenantId;
        this.status = status;
        this.processedClauses = processedClauses;
        this.totalClauses = totalClauses;
    }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public int getProcessedClauses() { return processedClauses; }
    public void setProcessedClauses(int processedClauses) { this.processedClauses = processedClauses; }

    public int getTotalClauses() { return totalClauses; }
    public void setTotalClauses(int totalClauses) { this.totalClauses = totalClauses; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}