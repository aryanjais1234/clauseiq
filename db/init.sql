CREATE EXTENSION IF NOT EXISTS vector;

---------------------------------------------------------
-- USER SERVICE
---------------------------------------------------------

CREATE TABLE tenants (
    id              UUID PRIMARY KEY,
    company_name    VARCHAR(255) NOT NULL,
    plan            VARCHAR(20) NOT NULL DEFAULT 'FREE',
    created_at      TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id              UUID PRIMARY KEY,
    tenant_id       UUID NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    role            VARCHAR(20) NOT NULL DEFAULT 'MEMBER',
    created_at      TIMESTAMP NOT NULL,

    CONSTRAINT fk_users_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenants(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_users_tenant
ON users(tenant_id);

---------------------------------------------------------
-- DOCUMENTS
---------------------------------------------------------

CREATE TABLE documents (
    id           UUID PRIMARY KEY,
    tenant_id    UUID NOT NULL,
    filename     VARCHAR(255) NOT NULL,
    kind         VARCHAR(20) NOT NULL,
    chunk_count  INT NOT NULL,
    created_at   TIMESTAMP NOT NULL,

    CONSTRAINT fk_documents_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenants(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_documents_tenant
ON documents(tenant_id);

---------------------------------------------------------
-- ANALYSIS JOBS
---------------------------------------------------------

CREATE TABLE analysis_jobs (
    id                UUID PRIMARY KEY,
    tenant_id         UUID NOT NULL,
    document_id       UUID NOT NULL,
    status            VARCHAR(20) NOT NULL,
    processed_clauses INT NOT NULL DEFAULT 0,
    total_clauses     INT NOT NULL DEFAULT 0,
    overall_risk      VARCHAR(20),
    total_tokens      INT NOT NULL DEFAULT 0,
    error_message     TEXT,
    created_at        TIMESTAMP NOT NULL,
    updated_at        TIMESTAMP,

    CONSTRAINT fk_jobs_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenants(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_jobs_document
        FOREIGN KEY (document_id)
        REFERENCES documents(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_jobs_tenant
ON analysis_jobs(tenant_id);

CREATE INDEX idx_jobs_document
ON analysis_jobs(document_id);

---------------------------------------------------------
-- CLAUSE FINDINGS
---------------------------------------------------------

CREATE TABLE clause_findings (
    id                 UUID PRIMARY KEY,
    job_id             UUID NOT NULL,
    tenant_id          UUID NOT NULL,
    clause_index       INT NOT NULL,
    clause_type        VARCHAR(40) NOT NULL,
    risk_level         VARCHAR(20) NOT NULL,
    summary            TEXT,
    playbook_deviation TEXT,
    suggested_redline  TEXT,
    created_at         TIMESTAMP NOT NULL,

    CONSTRAINT fk_findings_job
        FOREIGN KEY (job_id)
        REFERENCES analysis_jobs(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_findings_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenants(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_findings_job
ON clause_findings(job_id);

CREATE INDEX idx_findings_tenant
ON clause_findings(tenant_id);

---------------------------------------------------------
-- SPRING AI VECTOR STORE
---------------------------------------------------------

CREATE TABLE IF NOT EXISTS vector_store (
    id          UUID PRIMARY KEY,
    content     TEXT,
    metadata    JSONB,
    embedding   VECTOR(1536)
);

CREATE INDEX idx_vector_embedding
ON vector_store
USING HNSW (embedding vector_cosine_ops);