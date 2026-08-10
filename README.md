# ClauseIQ — AI Contract Analysis Service

ClauseIQ is an enterprise-grade multi-tenant microservices platform designed to automate contract review. It analyzes uploaded legal documents clause-by-clause against tenant-defined rules (playbooks) using Large Language Models (LLMs), Retrieval-Augmented Generation (RAG), and asynchronous event processing.

---

## 🏗️ System Architecture & Workflow

```
[ Client / UI ]
       │
       ▼
[ API Gateway ] ────(Authentication, Tenant Context & Rate Limiting)
       │
       ├───────────────────────────────┐
       ▼                               ▼
[ Ingestion Service ]         [ Contract Service ]
  (PDF ETL -> Vector Store)     (Job Init & Results API)
                                       │
                                       ▼
                                [ Kafka Topic ]
                             `analysis-requested`
                                       │
                                       ▼
                                [ AI Worker ] ◄──► [ Redis Cache ]
                          (RAG + LLM Clause Analysis)

```

1. **Authentication & Ingestion:** The client authenticates via JWT. Multi-tenant documents (playbooks and contracts) are uploaded through the [Ingestion Service](https://interview-prep-iota-pearl.vercel.app/clauseiq-simple.html#s-w-core), chunked into text segments, embedded, and stored in PostgreSQL (`pgvector`).
2. **Asynchronous Request:** The [Contract Service](https://interview-prep-iota-pearl.vercel.app/clauseiq-simple.html#s-w-core) accepts an analysis request, creates a job entry with status `QUEUED`, and emits an `AnalysisRequestedEvent` to Apache Kafka before immediately returning a `202 Accepted` response.
3. **AI Processing Pipeline:** The [AI Worker Service](https://interview-prep-iota-pearl.vercel.app/clauseiq-simple.html#s-w-core) consumes Kafka events, fetches contract chunks, retrieves matching tenant playbook rules via similarity search (RAG), checks Redis for cached clause assessments, and prompts OpenAI to evaluate risks.
4. **Real-time Progress & Results:** Processing updates are pushed back over Kafka to Redis Pub/Sub, streaming live updates to clients via Server-Sent Events (SSE). Completed reports and clause findings are persisted in PostgreSQL.

---

## 🛠️ Technology Stack

* **Java 21 & Spring Boot 3.5.5**
* **Spring AI (1.0.0):** Framework for model prompts, structured JSON outputs, and vector storage integrations.
* **Spring Cloud Gateway:** Central entry point handling JWT token parsing, tenant context extraction, and Redis-backed rate limiting.
* **Apache Kafka:** Asynchronous event streaming and message queuing.
* **PostgreSQL + pgvector:** Relational database for job management and HNSW vector index for embeddings (`text-embedding-3-small`).
* **Redis:** Operational cache for LLM responses, pub/sub progress streaming, and rate-limiting counters.

---

## 📁 Repository Structure

```text
clauseiq/
├── docker-compose.yml           # Local infrastructure setup (Postgres, Redis, Kafka)
├── db/
│   └── schema.sql              # Database DDL for relational tables & vector store
├── pom.xml                     # Parent Maven aggregator project
│
├── common/                     # Shared DTOs, Kafka events, Enums, and constants
├── api-gateway/                # Route forwarding, JWT validation, rate limiting
├── contract-service/           # Job REST API, SSE streaming, Kafka producer
├── ingestion-service/          # PDF upload, token splitting, and vector embedding
└── ai-worker/                  # Kafka consumer, RAG search, LLM analysis, Redis cache

```

---

## ⚡ Quick Start & Setup

### Prerequisites

* Java 21 JDK
* Maven 3.8+
* Docker & Docker Compose
* OpenAI API Key

### 1. Infrastructure Setup

Start the local PostgreSQL (pgvector), Redis, and Kafka containers:

```bash
docker compose up -d

```

### 2. Environment Variables

Set your credentials in your terminal environment:

```bash
export OPENAI_API_KEY="your-openai-api-key"
export JWT_SECRET="your-secret-key-must-be-at-least-32-characters"

```

### 3. Build the Project

Compile and build all modules from the root directory:

```bash
mvn clean install -DskipTests

```

### 4. Run the Microservices

Execute each service in its respective module directory:

```bash
# Terminal 1: API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 2: Contract Service
cd contract-service && mvn spring-boot:run

# Terminal 3: Ingestion Service
cd ingestion-service && mvn spring-boot:run

# Terminal 4: AI Worker Service
cd ai-worker && mvn spring-boot:run

```

---

## 📡 API Usage Guide

### Base URL

All API calls route through the Gateway at `http://localhost:8080`. Send your JWT in the header:
`Authorization: Bearer <JWT_TOKEN>`

### 1. Upload Playbook Document

Upload company rules or guidelines (typically done once per tenant):

```bash
curl -X POST http://localhost:8080/api/v1/ingestion/playbook \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@company-rules.pdf"

```

### 2. Upload Contract

Upload a vendor or legal contract for parsing:

```bash
curl -X POST http://localhost:8080/api/v1/ingestion/contract \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@vendor-contract.pdf"
# Response: { "documentId": "d3f1...", "chunks": 47 }

```

### 3. Start Contract Analysis

Submit a non-blocking request to analyze the uploaded contract:

```bash
curl -X POST http://localhost:8080/api/v1/contracts/{documentId}/analyze \
  -H "Authorization: Bearer $TOKEN"
# Response: 202 Accepted { "jobId": "a81b...", "status": "QUEUED" }

```

### 4. Check Job Status

Poll current progress:

```bash
curl http://localhost:8080/api/v1/analysis/{jobId} \
  -H "Authorization: Bearer $TOKEN"

```

### 5. Stream Real-Time Updates (SSE)

Receive real-time progress via Server-Sent Events:

```bash
curl -N http://localhost:8080/api/v1/analysis/{jobId}/stream \
  -H "Authorization: Bearer $TOKEN"

```

### 6. Retrieve Final Analysis Report

Fetch detailed clause findings and risk evaluations once complete:

```bash
curl http://localhost:8080/api/v1/analysis/{jobId}/report \
  -H "Authorization: Bearer $TOKEN"

```