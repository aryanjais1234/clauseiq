package com.clauseiq;

public class AppConstants {

    // Kafka topics
    public static final String TOPIC_ANALYSIS_REQUESTED = "analysis-requested";
    public static final String TOPIC_ANALYSIS_PROGRESS  = "analysis-progress";

    // Consumer group for the AI workers
    public static final String GROUP_AI_WORKERS = "ai-workers";

    // Headers that the gateway adds after checking the token.
    // The other services just read them, they never check the token again.
    public static final String HEADER_TENANT_ID = "X-Tenant-Id";
    public static final String HEADER_USER_ID   = "X-User-Id";
    public static final String HEADER_PLAN      = "X-Plan";

    // Redis channel used to send SSE updates between instances
    public static final String REDIS_PROGRESS_CHANNEL = "analysis-progress-channel";
}