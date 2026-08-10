CREATE EXTENSION IF NOT EXISTS vector;

-- Every uploaded file (contract or playbook)
CREATE TABLE documents (
    id           UUID PRIMARY KEY,
    tenant_id    VARCHAR(64)  NOT NULL,
    filename     VARCHAR(255) NOT NULL,
    kind         VARCHAR(20)  NOT NULL,     -- CONTRACT or PLAYBOOK
    chunk_count  INT          NOT NULL,
    created_at   TIMESTAMP    NOT NULL
);

-- One row per analysis request
CREATE TABLE analysis_jobs (
    id                UUID PRIMARY KEY,
    tenant_id         VARCHAR(64) NOT NULL,
    document_id       UUID        NOT NULL,
    status            VARCHAR(20) NOT NULL,   -- QUEUED, PROCESSING, COMPLETED, FAILED
    processed_clauses INT         NOT NULL DEFAULT 0,
    total_clauses     INT         NOT NULL DEFAULT 0,
    overall_risk      VARCHAR(20),
    total_tokens      INT         NOT NULL DEFAULT 0,
    error_message     TEXT,
    created_at        TIMESTAMP   NOT NULL,
    updated_at        TIMESTAMP
);

-- One row per analysed clause
CREATE TABLE clause_findings (
    id                 UUID PRIMARY KEY,
    job_id             UUID        NOT NULL,
    tenant_id          VARCHAR(64) NOT NULL,
    clause_index       INT         NOT NULL,
    clause_type        VARCHAR(40) NOT NULL,
    risk_level         VARCHAR(20) NOT NULL,
    summary            TEXT,
    playbook_deviation TEXT,
    suggested_redline  TEXT,
    created_at         TIMESTAMP   NOT NULL
);

-- Indexes we actually need
CREATE INDEX idx_jobs_tenant     ON analysis_jobs (tenant_id);
CREATE INDEX idx_findings_job    ON clause_findings (job_id);

-- Spring AI creates this table itself, but we create it here so the
-- vector index exists from the start. 1536 = size of text-embedding-3-small
CREATE TABLE IF NOT EXISTS vector_store (
    id        UUID PRIMARY KEY,
    content   TEXT,
    metadata  JSONB,
    embedding VECTOR(1536)
);

CREATE INDEX idx_vector_embedding ON vector_store
    USING HNSW (embedding vector_cosine_ops);