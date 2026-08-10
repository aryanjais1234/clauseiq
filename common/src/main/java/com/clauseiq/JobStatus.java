package com.clauseiq;

public enum JobStatus {

    QUEUED,       // saved in DB and sent to Kafka, waiting for a worker
    PROCESSING,   // a worker picked it up
    COMPLETED,
    FAILED;

    // Used to know when we can stop sending progress updates
    public boolean isFinished() {
        return this == COMPLETED || this == FAILED;
    }
}