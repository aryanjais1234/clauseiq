# ClauseIQ — Enterprise AI Contract Analysis Platform

<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.0-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-pgvector-blue)
![Redis](https://img.shields.io/badge/Redis-7-red)
![Apache Kafka](https://img.shields.io/badge/Apache-Kafka-black)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![License](https://img.shields.io/badge/License-MIT-green)

</p>

---

# 📖 Overview

ClauseIQ is an **enterprise-grade AI-powered Contract Analysis Platform** built using a **microservices architecture**. The platform automates legal contract review by comparing uploaded contracts against organization-specific legal playbooks using **Large Language Models (LLMs)** and **Retrieval-Augmented Generation (RAG)**.

Instead of manually reviewing contracts clause-by-clause, legal teams can upload their company playbook once and allow ClauseIQ to automatically detect:

- Risky clauses
- Missing clauses
- Policy violations
- Suggested legal modifications
- Clause summaries
- Overall contract risk

The platform is designed as a **multi-tenant SaaS application**, allowing multiple organizations to securely use the same system while keeping their data completely isolated.

---

# ❓ Why ClauseIQ?

Contract review is one of the most time-consuming tasks performed by legal teams.

A single enterprise contract may contain **50–200 clauses**, requiring legal professionals to manually compare each clause against internal legal guidelines.

This process is:

- Slow
- Expensive
- Error-prone
- Difficult to scale

Traditional keyword search systems cannot understand legal meaning.

ClauseIQ solves this problem using:

- Artificial Intelligence
- Vector Search
- Retrieval-Augmented Generation (RAG)
- Event Driven Architecture
- Distributed Microservices

---

# 🎯 Business Problem

Imagine a company receives a vendor contract.

The legal team needs to verify:

✔ Does the contract violate company policy?

✔ Is the payment clause acceptable?

✔ Is the liability clause too risky?

✔ Is the termination clause missing?

✔ Does the NDA clause match company standards?

Reviewing these manually may take several hours.

With ClauseIQ:

```
Upload Playbook
        │
        ▼
Upload Contract
        │
        ▼
AI compares every clause
        │
        ▼
Risk Report generated
        │
        ▼
Legal team reviews only flagged clauses
```

The review time is reduced from **hours to minutes**.

---

# 🚀 Key Features

## Authentication

- User Registration
- User Login
- JWT Token Generation
- Multi-Tenant Authentication
- Role Based Access (Admin / Member)

---

## Multi-Tenant SaaS

Each organization owns its own:

- Users
- Contracts
- Playbooks
- Analysis Jobs
- Vector Embeddings

Tenant isolation is maintained throughout the entire system.

---

## AI Contract Review

The platform automatically performs:

- Clause Identification
- Clause Summarization
- Risk Assessment
- Policy Matching
- Suggested Redlines
- Overall Risk Calculation

---

## Retrieval-Augmented Generation (RAG)

Instead of sending the entire playbook to the LLM,

ClauseIQ:

- Chunks playbooks
- Creates embeddings
- Stores vectors
- Retrieves only relevant sections
- Sends only contextual information to the LLM

This improves:

- Accuracy
- Speed
- Cost

---

## Event Driven Architecture

Contract analysis is completely asynchronous.

Instead of waiting for the AI model to finish,

the system immediately returns:

```
HTTP 202 Accepted
```

while processing continues in the background.

---

## Live Progress Tracking

Users can monitor analysis progress in real time.

Example:

```
Queued

↓

Processing

↓

Clause 12 / 47

↓

Clause 28 / 47

↓

Completed
```

---

## Vector Search

Playbooks are converted into embeddings and stored inside **PostgreSQL pgvector**.

Similarity Search retrieves only the most relevant company rules for each contract clause.

---

## Enterprise Ready Design

✔ Spring Boot Microservices

✔ API Gateway

✔ Apache Kafka

✔ Redis

✔ PostgreSQL

✔ pgvector

✔ Spring AI

✔ OpenAI

✔ Docker

✔ JWT Authentication

✔ Multi-Tenant Architecture

---

# 🏗 High Level Architecture

```
                                   ┌─────────────────────────────┐
                                   │         Client / UI         │
                                   │  Web • Mobile • Postman     │
                                   └──────────────┬──────────────┘
                                                  │
                                                  │
                                          JWT Authentication
                                                  │
                                                  ▼
                         ┌───────────────────────────────────────────────┐
                         │                 API Gateway                   │
                         │-----------------------------------------------│
                         │ • Route Forwarding                            │
                         │ • JWT Validation                              │
                         │ • Tenant Extraction                           │
                         │ • Rate Limiting                               │
                         │ • Authentication                              │
                         └──────────────┬────────────────────────────────┘
                                        │
            ┌───────────────────────────┼────────────────────────────┐
            │                           │                            │
            ▼                           ▼                            ▼
 ┌──────────────────┐      ┌──────────────────────┐      ┌────────────────────┐
 │   User Service   │      │  Ingestion Service   │      │ Contract Service   │
 │------------------│      │----------------------│      │--------------------│
 │ Register         │      │ Upload PDF           │      │ Create Job         │
 │ Login            │      │ Extract Text         │      │ Kafka Producer     │
 │ Generate JWT     │      │ Chunk Documents      │      │ SSE Updates        │
 │ Tenant Mgmt      │      │ Create Embeddings    │      │ Report APIs        │
 └────────┬─────────┘      └──────────┬───────────┘      └─────────┬──────────┘
          │                           │                            │
          │                           ▼                            │
          │               PostgreSQL + pgvector                    │
          │                           ▲                            │
          │                           │                            ▼
          │                    Vector Search                 Apache Kafka
          │                                                    Topic
          │                                                      │
          │                                                      ▼
          │                                         ┌────────────────────────┐
          │                                         │      AI Worker         │
          │                                         │------------------------│
          │                                         │ Consume Events         │
          │                                         │ RAG Retrieval          │
          │                                         │ OpenAI Analysis        │
          │                                         │ Redis Cache            │
          │                                         │ Store Findings         │
          │                                         └──────────┬─────────────┘
          │                                                    │
          │                                                    ▼
          │                                              Redis Cache
          │
          ▼
   PostgreSQL
```

---

# 🔄 Complete Request Workflow

```
User Login
    │
    ▼
User Service
    │
    ▼
JWT Generated
    │
    ▼
API Gateway
    │
    ▼
Upload Playbook
    │
    ▼
Ingestion Service
    │
    ▼
Chunking
    │
    ▼
Embedding Generation
    │
    ▼
pgvector Storage
    │
    ▼
Upload Contract
    │
    ▼
Contract Service
    │
    ▼
Create Analysis Job
    │
    ▼
Kafka Event
    │
    ▼
AI Worker
    │
    ▼
Retrieve Similar Playbook Rules
    │
    ▼
OpenAI Analysis
    │
    ▼
Save Findings
    │
    ▼
Stream Progress
    │
    ▼
Generate Final Report
```

---

# 🛠 Technology Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.5 |
| AI Framework | Spring AI 1.0 |
| LLM | OpenAI GPT |
| API Gateway | Spring Cloud Gateway |
| Authentication | Spring Security + JWT |
| Database | PostgreSQL |
| Vector Database | pgvector |
| Cache | Redis |
| Event Streaming | Apache Kafka |
| ORM | Spring Data JPA |
| Build Tool | Maven |
| Containerization | Docker & Docker Compose |

---

# 📂 Repository Structure

```
clauseiq/
│
├── docker-compose.yml
│
├── db/
│   └── schema.sql
│
├── pom.xml
│
├── common/
│
├── api-gateway/
│
├── user-service/
│
├── ingestion-service/
│
├── contract-service/
│
└── ai-worker/
```

---

# 📦 Repository Overview

| Module | Responsibility |
|----------|----------------|
| **common** | Shared DTOs, Kafka Events, Constants and Enums |
| **api-gateway** | JWT Validation, Request Routing, Tenant Context, Rate Limiting |
| **user-service** | User Registration, Login, Tenant Management and JWT Generation |
| **ingestion-service** | PDF Upload, Text Extraction, Chunking, Embedding Generation |
| **contract-service** | Contract Analysis APIs, Kafka Producer, Progress Streaming |
| **ai-worker** | Kafka Consumer, Retrieval-Augmented Generation, OpenAI Integration |

---

# 🎯 Design Goals

ClauseIQ is designed around the following engineering principles:

- Separation of Concerns
- Loose Coupling
- High Scalability
- Asynchronous Processing
- Fault Isolation
- Stateless Services
- Enterprise Security
- Tenant Isolation
- AI-first Architecture

These principles make the platform suitable for enterprise-scale deployments where thousands of contracts can be processed concurrently.

---

# Phase 2 — Microservice Deep Dive

ClauseIQ follows a **distributed microservices architecture**, where each service is responsible for a single business capability.

Instead of building one large monolithic application, the platform is divided into multiple independent services that communicate through **REST APIs** and **Apache Kafka**.

This architecture provides:

- Better scalability
- Loose coupling
- Independent deployment
- Fault isolation
- Easier maintenance
- Technology flexibility
- Event-driven processing

---

# Overall Microservice Communication

```
                           Client
                              │
                              ▼
                      API Gateway
                              │
        ┌─────────────┬──────────────┬───────────────┐
        ▼             ▼              ▼               ▼
 User Service   Ingestion Service  Contract Service  Common
                                      │
                                      ▼
                                   Kafka
                                      │
                                      ▼
                                 AI Worker
                                      │
                       ┌──────────────┴──────────────┐
                       ▼                             ▼
                 PostgreSQL                     Redis
```

Every microservice has a well-defined responsibility.

---

# 1. User Service

## Purpose

The User Service is responsible for managing platform users and organizations (tenants).

It acts as the identity provider for the entire application.

Unlike other services, it is **the only service allowed to create JWT tokens**.

---

## Responsibilities

- User Registration
- User Login
- Password Encryption
- Tenant Creation
- User Management
- JWT Generation
- Role Management

---

## Why Separate User Service?

Authentication is a separate business capability.

Keeping it isolated provides several benefits.

- Independent scaling
- Easier migration to OAuth2 later
- No authentication logic inside business services
- Better security
- Easier maintenance

---

## Internal Workflow

```
Register Request
       │
       ▼
Validate Input
       │
       ▼
Create Tenant
       │
       ▼
Encrypt Password
       │
       ▼
Create User
       │
       ▼
Return Response
```

---

### Login Flow

```
Login Request
      │
      ▼
Find User
      │
      ▼
Verify Password
      │
      ▼
Generate JWT
      │
      ▼
Return Token
```

---

## Database Tables

The User Service owns:

```
tenants

users
```

No other microservice modifies these tables.

---

## Main APIs

```
POST /api/v1/auth/register

POST /api/v1/auth/login
```

Future APIs

```
GET /users

PUT /users

DELETE /users

GET /profile

POST /invite-member
```

---

# 2. API Gateway

## Purpose

The API Gateway is the single entry point for every client request.

Clients never directly communicate with internal microservices.

Instead,

```
Client

↓

Gateway

↓

Microservices
```

---

## Responsibilities

- Request Routing
- JWT Validation
- Authentication
- Tenant Context Extraction
- Rate Limiting
- Header Injection
- Central Security

---

## Why API Gateway?

Without Gateway

```
Client

↓

User Service

↓

Contract Service

↓

Worker

↓

Ingestion
```

Every service must implement

- JWT validation
- Authentication
- Authorization
- Tenant extraction

Result:

Lots of duplicate code.

---

With Gateway

```
Client

↓

Gateway

↓

JWT Validation

↓

Extract Claims

↓

Forward Request

↓

Services
```

Authentication happens only once.

---

## Gateway Flow

```
Incoming Request

↓

Read JWT

↓

Validate Signature

↓

Extract

UserId

TenantId

Role

↓

Inject Headers

↓

Forward Request
```

The downstream services no longer need to understand JWT.

Instead they simply receive

```
X-User-Id

X-Tenant-Id

X-Role
```

---

## Benefits

- Centralized Authentication
- Stateless Services
- Less Boilerplate
- Better Security
- Easier Maintenance

---

# 3. Ingestion Service

## Purpose

The Ingestion Service converts uploaded PDF documents into AI-searchable knowledge.

It is responsible for creating the knowledge base used later by Retrieval-Augmented Generation (RAG).

---

## Responsibilities

- File Upload
- PDF Parsing
- Text Cleaning
- Chunk Creation
- Embedding Generation
- Vector Storage

---

## Why Separate Service?

PDF processing is CPU intensive.

Embedding generation is expensive.

Keeping it isolated prevents slowing down other services.

---

## Workflow

```
Upload PDF

↓

Extract Text

↓

Split into Chunks

↓

Generate Embeddings

↓

Store in pgvector
```

---

## Why Chunk Documents?

Large Language Models have context limits.

Example

```
Entire Playbook

↓

300 Pages

↓

Impossible to send

↓

Chunk into

500–1000 token blocks
```

Each chunk becomes independently searchable.

---

## Embedding Pipeline

```
Chunk

↓

OpenAI Embedding Model

↓

1536 Dimension Vector

↓

Store in PostgreSQL
```

---

## Database

The Ingestion Service manages

```
documents

vector_store
```

---

## APIs

```
POST /playbook

POST /contract
```

Future

```
DELETE Document

Re-index Document

List Uploaded Files
```

---

# 4. Contract Service

## Purpose

The Contract Service manages the complete contract analysis lifecycle.

It does **not** perform AI processing.

Instead, it coordinates the workflow.

---

## Responsibilities

- Create Analysis Job
- Manage Job Status
- Produce Kafka Events
- Store Analysis Results
- Stream Progress
- Expose Report APIs

---

## Internal Workflow

```
Receive Request

↓

Create Job

↓

Status = QUEUED

↓

Publish Kafka Event

↓

Return HTTP 202
```

Notice

The service never waits for AI.

This makes the API highly responsive.

---

## Why Asynchronous?

AI processing may take

- 30 seconds
- 2 minutes
- 5 minutes

Waiting for HTTP would block resources.

Instead

```
Client

↓

202 Accepted

↓

Worker Processes

↓

Results Later
```

---

## Kafka Producer

Produces

```
AnalysisRequestedEvent
```

Topic

```
analysis-requested
```

---

## Database

```
analysis_jobs

clause_findings
```

---

## APIs

```
POST /contracts/{id}/analyze

GET /analysis/{jobId}

GET /analysis/{jobId}/report

GET /analysis/{jobId}/stream
```

---

# 5. AI Worker

## Purpose

The AI Worker is the brain of the platform.

It performs all AI-related processing.

Unlike REST services, it is completely event-driven.

---

## Responsibilities

- Kafka Consumer
- RAG Retrieval
- Prompt Engineering
- OpenAI Calls
- Redis Cache
- Save Findings
- Progress Updates

---

## Complete Workflow

```
Kafka Event

↓

Load Contract

↓

Split Clauses

↓

Retrieve Playbook Rules

↓

Check Redis Cache

↓

Prompt OpenAI

↓

Receive JSON

↓

Save Findings

↓

Publish Progress

↓

Completed
```

---

## Why Separate Worker?

AI processing is:

- Slow
- CPU intensive
- Token expensive

Separating the worker allows independent scaling.

Need more throughput?

Simply add more workers.

```
Kafka

↓

Worker 1

Worker 2

Worker 3

Worker 4
```

Kafka automatically distributes messages.

---

## Redis Usage

Redis stores

```
Prompt Cache

Clause Cache

Progress

Temporary State
```

This avoids repeated LLM calls.

---

## Database

Writes

```
analysis_jobs

clause_findings
```

Reads

```
vector_store

documents
```

---

# 6. Common Module

## Purpose

The Common module contains all shared code used across microservices.

It prevents code duplication.

---

## Responsibilities

```
Enums

DTOs

Kafka Events

Constants

Shared Models
```

---

## Example

```
AnalysisRequestedEvent
```

Instead of creating the class twice

```
Contract Service

AI Worker
```

both simply import

```
common
```

This guarantees

- identical serialization
- identical field names
- compile-time safety

---

## Current Shared Components

```
AnalysisRequestedEvent

AnalysisProgressEvent

JobStatus

Common Constants
```

Future additions may include

```
JWT Utilities

API Response

Exception Models

Shared Validation

Error Codes
```

---

# Inter-Service Communication

```
                      REST APIs
      ┌─────────────────────────────────────┐
      │                                     │
      ▼                                     ▼
User Service                    Ingestion Service
      ▲                                     ▲
      │                                     │
      └──────────────Gateway─────────────────┘


                Event Driven

Contract Service

↓

Kafka

↓

AI Worker

↓

Kafka

↓

Contract Service
```

REST is used for synchronous operations.

Kafka is used for long-running asynchronous operations.

---

# Why This Architecture?

The system follows the **Single Responsibility Principle** at the service level.

| Service | Responsibility |
|----------|----------------|
| User Service | Authentication & User Management |
| API Gateway | Security & Routing |
| Ingestion Service | Document Processing |
| Contract Service | Analysis Lifecycle |
| AI Worker | Artificial Intelligence |
| Common | Shared Contracts |

Each service can evolve, scale, and deploy independently without affecting others.

---
# Phase 3 — Authentication, Authorization & Multi-Tenant Architecture

Authentication and tenant isolation are two of the most important aspects of ClauseIQ.

Since the platform is designed as a **multi-tenant SaaS application**, every request must be associated with:

- The authenticated user
- The organization (tenant)
- The user's role

This ensures that users can only access data belonging to their own organization.

---

# Authentication Overview

ClauseIQ uses **JWT (JSON Web Token)** based authentication.

Unlike traditional session-based authentication, JWT is:

- Stateless
- Scalable
- Fast
- Suitable for distributed microservices

The authentication responsibility is divided between two services.

```
                User Service
                     │
        Generates JWT Token
                     │
                     ▼
                API Gateway
                     │
        Validates JWT Token
                     │
                     ▼
             Forward Request
```

This separation keeps authentication centralized while allowing every business service to remain stateless.

---

# Why JWT?

Using server-side sessions in a microservice architecture introduces several challenges.

```
Session

↓

Shared Memory

↓

Sticky Sessions

↓

Scaling Problems
```

JWT solves these issues because all authentication information is stored inside the token itself.

Benefits:

- No server-side session storage
- Horizontal scalability
- Stateless authentication
- Suitable for distributed systems
- Faster request processing

---

# Authentication Flow

```
                    User
                     │
                     │ Login
                     ▼
              User Service
                     │
         Validate Credentials
                     │
                     ▼
              Generate JWT
                     │
                     ▼
               Return Token
                     │
                     ▼
                 Client Stores
                Authorization Token
```

Every future request includes:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# User Registration Flow

```
Client
   │
POST /register
   │
   ▼
User Service
   │
Validate Request
   │
Create Tenant
   │
Encrypt Password
   │
Create User
   │
Store Database
   │
Return Success
```

During registration:

- A new tenant is created.
- The first registered user becomes the tenant administrator.
- Passwords are stored using BCrypt hashing.

No plain-text passwords are ever stored.

---

# User Login Flow

```
Client

↓

POST /login

↓

User Service

↓

Find User

↓

Verify Password

↓

Generate JWT

↓

Return JWT

↓

Client Stores Token
```

The User Service is the **only service** responsible for creating JWT tokens.

---

# JWT Payload

The generated JWT contains user information required by downstream services.

Example payload:

```json
{
  "sub": "admin@company.com",
  "userId": "3e6f2b12...",
  "tenantId": "f2bce9a5...",
  "role": "ADMIN",
  "iat": 1723652345,
  "exp": 1723738745
}
```

---

## Claim Explanation

| Claim | Description |
|--------|-------------|
| sub | User email |
| userId | Unique user identifier |
| tenantId | Organization identifier |
| role | User role |
| iat | Token creation time |
| exp | Token expiration time |

---

# Why Store Tenant ID Inside JWT?

Every request must know which organization owns the data.

Without storing the tenant information inside the token,

every request would require an additional database lookup.

```
Request

↓

Read JWT

↓

Extract Tenant

↓

Continue
```

Instead of

```
Request

↓

Database Query

↓

Find Tenant

↓

Continue
```

Embedding the tenant ID inside the JWT significantly reduces latency.

---

# API Gateway Authentication

Every request enters the system through the API Gateway.

```
                Client
                   │
                   ▼
             API Gateway
```

The gateway performs:

- JWT validation
- Token expiration verification
- Signature verification
- Claim extraction
- User context creation

Only authenticated requests are forwarded.

---

# Gateway Request Flow

```
Incoming Request

↓

Read Authorization Header

↓

Extract JWT

↓

Validate Signature

↓

Check Expiration

↓

Extract Claims

↓

Forward Request
```

If validation fails:

```
401 Unauthorized
```

is returned immediately.

The request never reaches downstream services.

---

# JWT Validation Flow

```
Authorization Header

↓

Extract Token

↓

Verify Secret Key

↓

Verify Signature

↓

Check Expiration

↓

Extract Claims

↓

Authentication Success
```

---

# Header Injection

After validating the token, the Gateway extracts user information.

Instead of forwarding the JWT to every service,

the Gateway forwards only the required user context.

```
X-User-Id

X-Tenant-Id

X-Role
```

Example:

```
POST /contracts

Headers

Authorization: Bearer eyJhb...

↓

Gateway

↓

Headers Added

X-User-Id: 91d3...

X-Tenant-Id: f29b...

X-Role: ADMIN
```

Business services no longer need to understand JWT.

---

# Benefits of Header Injection

Without Gateway

```
Every Service

↓

Read JWT

↓

Validate JWT

↓

Extract Claims
```

Duplicate code exists everywhere.

With Gateway

```
Gateway

↓

Validate Once

↓

Forward Context

↓

Business Services
```

Authentication logic exists only in one place.

---

# Request Lifecycle

```
User

↓

Login

↓

JWT Generated

↓

Store Token

↓

Upload Contract

↓

Gateway

↓

Validate JWT

↓

Extract Tenant

↓

Forward Request

↓

Business Service
```

---

# Authorization

Authentication verifies **who the user is**.

Authorization determines **what the user is allowed to do**.

Example roles:

```
ADMIN

MEMBER
```

Possible permissions:

| Action | ADMIN | MEMBER |
|----------|-------|---------|
| Upload Playbook | ✔ | ❌ |
| Upload Contract | ✔ | ✔ |
| Start Analysis | ✔ | ✔ |
| Delete Documents | ✔ | ❌ |
| Invite Users | ✔ | ❌ |

Role information is extracted directly from the JWT.

---

# Multi-Tenant Architecture

ClauseIQ is designed as a **shared database, shared application** multi-tenant platform.

```
                    ClauseIQ

        ┌─────────────┬─────────────┐
        │             │             │
        ▼             ▼             ▼

   Company A     Company B     Company C
```

Each company has:

- Independent users
- Independent contracts
- Independent playbooks
- Independent analysis jobs
- Independent vector embeddings

No tenant can access another tenant's data.

---

# Tenant Isolation

Every business table contains a tenant identifier.

Example:

```
users

↓

tenant_id

documents

↓

tenant_id

analysis_jobs

↓

tenant_id

clause_findings

↓

tenant_id
```

Every query filters data using the tenant identifier.

Example:

```
SELECT *

FROM documents

WHERE tenant_id = ?
```

This guarantees complete data isolation.

---

# Complete Request Journey

```
Client

↓

Login

↓

JWT Generated

↓

Upload Contract

↓

Gateway

↓

Validate Token

↓

Extract Tenant

↓

Forward Request

↓

Contract Service

↓

Create Job

↓

Kafka

↓

AI Worker

↓

Store Findings

↓

Return Report
```

The tenant context remains attached to the request throughout the entire lifecycle.

---

# Why Keep Authentication Separate?

The User Service focuses only on identity management.

The API Gateway focuses only on authentication and routing.

Business services focus only on business logic.

This separation provides:

- Better maintainability
- Independent deployments
- Reduced coupling
- Improved scalability
- Cleaner architecture

---

# Authentication Responsibilities

| Component | Responsibility |
|------------|----------------|
| User Service | Register users, Login users, Generate JWT |
| API Gateway | Validate JWT, Extract Claims, Authenticate Requests |
| Contract Service | Business Logic Only |
| Ingestion Service | Business Logic Only |
| AI Worker | Event Processing Only |

---

# Design Principles

The authentication architecture follows several software engineering principles:

- Single Responsibility Principle
- Separation of Concerns
- Stateless Authentication
- Centralized Security
- Tenant Isolation
- Loose Coupling

This design allows authentication logic to evolve independently from business functionality and makes the platform easier to scale as additional microservices are introduced.

---

# Phase 4 — AI Processing Pipeline (RAG + LLM + Kafka)

The AI Processing Pipeline is the core of ClauseIQ.

This pipeline is responsible for transforming uploaded legal documents into meaningful business insights by combining:

- Retrieval-Augmented Generation (RAG)
- Vector Search
- OpenAI LLM
- Apache Kafka
- Redis Cache
- Spring AI

Unlike traditional AI applications that simply send an entire document to an LLM, ClauseIQ retrieves only the most relevant legal rules before generating an answer. This significantly improves response quality while reducing token usage and inference cost.

---

# AI Pipeline Overview

```
                      Upload Playbook
                              │
                              ▼
                     Ingestion Service
                              │
                     Extract PDF Text
                              │
                              ▼
                        Chunk Document
                              │
                              ▼
                  Generate Embeddings
                              │
                              ▼
                 Store in PostgreSQL
                        (pgvector)
                              │
────────────────────────────────────────────────────────────

                     Upload Contract
                              │
                              ▼
                    Contract Service
                              │
                              ▼
                 Create Analysis Job
                              │
                              ▼
                Publish Kafka Event
                              │
                              ▼
                      AI Worker
                              │
                              ▼
                Retrieve Contract
                              │
                              ▼
              Retrieve Playbook Rules
                  using Vector Search
                              │
                              ▼
                   Check Redis Cache
                              │
                              ▼
                    Build AI Prompt
                              │
                              ▼
                     OpenAI Model
                              │
                              ▼
                  Structured JSON Output
                              │
                              ▼
             Save Findings in PostgreSQL
                              │
                              ▼
             Publish Progress Events
                              │
                              ▼
                     Final Report
```

---

# Why Not Send the Entire Playbook?

Suppose a company uploads:

```
Employee Handbook

250 Pages

Vendor Guidelines

180 Pages

Security Policy

120 Pages

Legal Playbook

300 Pages
```

Total:

```
850+ Pages
```

Now suppose a contract contains only this clause:

```
Termination Clause
```

Sending the entire playbook to the LLM would be:

- Very expensive
- Very slow
- Mostly irrelevant
- May exceed model context limits

Instead, ClauseIQ retrieves only the most relevant rules.

---

# Retrieval-Augmented Generation (RAG)

ClauseIQ uses **Retrieval-Augmented Generation (RAG)** to provide context-aware responses.

Instead of relying only on the LLM's pre-trained knowledge, the system retrieves relevant company-specific documents before generating an answer.

```
Question

↓

Retrieve Relevant Documents

↓

Build Context

↓

LLM

↓

Answer
```

This allows the AI to reason using the organization's own legal policies.

---

# What is a Playbook?

A playbook contains the organization's legal standards.

Examples include:

- Procurement Policies
- Vendor Guidelines
- NDA Standards
- Security Policies
- Compliance Rules
- Payment Guidelines
- Risk Thresholds

These documents become the AI's knowledge base.

---

# Step 1 — Playbook Upload

```
Client

↓

Upload PDF

↓

Ingestion Service
```

Supported documents:

- Company Playbook
- Vendor Policy
- Security Standards
- Procurement Rules
- Compliance Manuals

The uploaded document is stored before processing begins.

---

# Step 2 — PDF Parsing

The Ingestion Service extracts text from the uploaded PDF.

```
PDF

↓

Extract Raw Text

↓

Clean Text

↓

Normalize

↓

Ready for Chunking
```

During preprocessing:

- Remove unnecessary formatting
- Normalize whitespace
- Preserve legal numbering
- Preserve clause hierarchy

---

# Step 3 — Document Chunking

Large Language Models cannot efficiently process extremely large documents.

Therefore, the extracted text is divided into smaller chunks.

Example:

```
300 Page Playbook

↓

Chunk 1

Chunk 2

Chunk 3

...

Chunk 500
```

Each chunk typically contains a manageable amount of text suitable for embedding generation.

---

# Why Chunking?

Without chunking:

```
Entire Document

↓

Embedding

↓

Poor Search
```

With chunking:

```
Chunk 1

Chunk 2

Chunk 3

↓

Independent Search
```

Benefits:

- Better similarity search
- More accurate retrieval
- Smaller prompts
- Lower token cost

---

# Step 4 — Embedding Generation

Each chunk is converted into a numerical vector.

```
Chunk

↓

Embedding Model

↓

1536 Dimension Vector
```

Example:

```
Clause

↓

[0.27, -0.91, 0.54, ....]
```

The vector captures the semantic meaning of the text rather than exact keywords.

---

# Why Embeddings?

Traditional search:

```
Payment

↓

Find word "Payment"
```

Semantic search:

```
Invoice

Billing

Compensation

Payment

Fees
```

All can be considered semantically related even if the exact keyword is absent.

---

# Step 5 — Vector Storage

Generated embeddings are stored inside PostgreSQL using **pgvector**.

```
Chunk

↓

Embedding

↓

vector_store Table
```

Each stored vector contains:

- Chunk Content
- Metadata
- Embedding Vector

---

# Vector Store

```
vector_store

───────────────

id

content

metadata

embedding
```

Metadata may include:

- Tenant
- Document
- Chunk Number
- Document Type

---

# Why PostgreSQL + pgvector?

Instead of maintaining a separate vector database, pgvector allows ClauseIQ to:

- Store relational data
- Store embeddings
- Execute similarity search
- Keep transactional consistency

Using a single database simplifies deployment and maintenance.

---

# Similarity Search

When a contract clause arrives:

```
Clause

↓

Generate Embedding

↓

Compare with Stored Vectors

↓

Top K Matches
```

The nearest vectors represent the most relevant company policies.

---

# Vector Search Flow

```
Contract Clause

↓

Embedding

↓

pgvector

↓

Cosine Similarity

↓

Top 5 Playbook Rules
```

Only these retrieved rules are included in the prompt.

---

# Step 6 — Contract Upload

Unlike playbooks, contracts are analyzed.

```
Upload Contract

↓

Store Metadata

↓

Create Analysis Job
```

The contract is divided into clauses for independent analysis.

---

# Step 7 — Kafka Event

The Contract Service publishes:

```
AnalysisRequestedEvent
```

Topic:

```
analysis-requested
```

Example event:

```
Job ID

Tenant ID

Document ID

Requested By
```

The API immediately returns:

```
HTTP 202 Accepted
```

without waiting for AI.

---

# Step 8 — AI Worker

The AI Worker consumes Kafka events.

```
Kafka

↓

AI Worker

↓

Start Processing
```

Unlike REST APIs, workers operate asynchronously.

---

# Clause-by-Clause Processing

Instead of analyzing the entire contract at once:

```
Contract

↓

Clause 1

Clause 2

Clause 3

...

Clause N
```

Each clause is analyzed independently.

Benefits:

- Better accuracy
- Easier retries
- Parallel processing
- Progress tracking

---

# Redis Cache

Before calling OpenAI:

```
Clause

↓

Redis

↓

Found?

↓

Yes → Return Cached Result

No

↓

Call OpenAI
```

Caching avoids repeated AI requests for identical clauses.

Benefits:

- Lower latency
- Reduced API cost
- Higher throughput

---

# Prompt Construction

After retrieving the relevant playbook rules, the worker constructs a prompt.

```
Contract Clause

+

Relevant Playbook Rules

+

Instructions

↓

Prompt
```

Only the required context is sent.

This keeps prompts:

- Smaller
- Faster
- Cheaper
- More accurate

---

# OpenAI Analysis

The prompt is submitted to the LLM.

The model evaluates:

- Clause Type
- Risk Level
- Policy Compliance
- Violations
- Suggested Changes
- Summary

The response is returned in structured JSON format.

---

# Example AI Output

```
Clause Type

Termination

↓

Risk

HIGH

↓

Summary

Notice period exceeds policy.

↓

Suggestion

Reduce termination notice
to 30 days.
```

Using structured JSON makes the output easy to parse and store.

---

# Persisting Results

Each analyzed clause is stored independently.

```
AI Response

↓

clause_findings
```

The overall job status is also updated.

```
analysis_jobs

↓

PROCESSING

↓

COMPLETED
```

---

# Progress Updates

After processing each clause:

```
Processed Clause

↓

AnalysisProgressEvent

↓

Kafka

↓

Contract Service

↓

Client
```

The client receives live progress updates.

Example:

```
2 / 47

↓

13 / 47

↓

29 / 47

↓

47 / 47
```

---

# End-to-End AI Flow

```
Playbook

↓

Chunk

↓

Embedding

↓

Vector Store

────────────────────────────

Contract

↓

Create Job

↓

Kafka

↓

AI Worker

↓

Split Clauses

↓

Generate Embedding

↓

Vector Search

↓

Retrieve Company Rules

↓

Redis Cache

↓

OpenAI

↓

Structured Output

↓

Save Findings

↓

Update Progress

↓

Generate Report
```

---

# Why Use Kafka?

AI inference is time-consuming.

Without Kafka:

```
Client

↓

Wait 2 Minutes

↓

Timeout
```

With Kafka:

```
Client

↓

202 Accepted

↓

Kafka Queue

↓

Worker

↓

Processing

↓

Results Later
```

The user is never blocked while AI processing continues in the background.

---

# Why Use Redis?

Redis improves performance by:

- Caching AI responses
- Reducing duplicate OpenAI calls
- Storing temporary processing state
- Publishing live progress updates

This decreases response time and infrastructure cost.

---

# Why Use Spring AI?

Spring AI provides a unified abstraction for interacting with Large Language Models.

It simplifies:

- Prompt creation
- Structured JSON responses
- Embedding generation
- Vector store integration

Instead of manually calling model APIs, developers work with higher-level abstractions that integrate naturally with Spring Boot.

---

# Design Principles

The AI pipeline follows several architectural principles:

- Retrieval before Generation
- Asynchronous Processing
- Event-Driven Communication
- Stateless Workers
- Context-Aware AI
- Cost Optimization
- Independent Scaling

By separating ingestion, orchestration, retrieval, and inference into dedicated components, ClauseIQ can process large volumes of contracts efficiently while maintaining high accuracy and keeping LLM usage economical.

---

# Phase 5 — Database Design & Data Model

ClauseIQ uses **PostgreSQL** as the primary relational database and **pgvector** as the vector storage engine.

Unlike many AI applications that require a separate vector database, ClauseIQ stores both structured business data and vector embeddings inside PostgreSQL.

This provides:

- ACID Transactions
- Relational Consistency
- Vector Search
- Easier Deployment
- Lower Infrastructure Cost

---

# Database Overview

```
                        PostgreSQL
                              │
      ┌───────────────────────┼────────────────────────┐
      │                       │                        │
      ▼                       ▼                        ▼
  User Data             Business Data          AI Knowledge Base
      │                       │                        │
      ▼                       ▼                        ▼
 tenants              analysis_jobs          vector_store
 users                clause_findings
                      documents
```

The database stores two types of information:

- Relational Data
- Vector Embeddings

---

# Entity Relationship Diagram (ER Diagram)

```
                     +----------------------+
                     |      TENANTS         |
                     +----------------------+
                     | id (PK)              |
                     | company_name         |
                     | plan                 |
                     | created_at           |
                     +----------+-----------+
                                |
                     One Tenant
                                |
                                |
                 +--------------+---------------+
                 |                              |
                 |                              |
                 ▼                              ▼

        +-------------------+         +---------------------+
        |      USERS        |         |     DOCUMENTS       |
        +-------------------+         +---------------------+
        | id (PK)           |         | id (PK)            |
        | tenant_id (FK)    |         | tenant_id (FK)     |
        | email             |         | filename           |
        | password_hash     |         | kind               |
        | role              |         | chunk_count        |
        | created_at        |         | created_at         |
        +-------------------+         +----------+---------+
                                                 |
                                                 |
                                                 |
                                                 ▼

                                      +------------------------+
                                      |    ANALYSIS_JOBS       |
                                      +------------------------+
                                      | id (PK)               |
                                      | tenant_id (FK)        |
                                      | document_id (FK)      |
                                      | status               |
                                      | processed_clauses    |
                                      | total_clauses        |
                                      | overall_risk         |
                                      | total_tokens         |
                                      | error_message        |
                                      | created_at           |
                                      | updated_at           |
                                      +-----------+----------+
                                                  |
                                                  |
                                                  ▼

                                    +-----------------------------+
                                    |     CLAUSE_FINDINGS         |
                                    +-----------------------------+
                                    | id (PK)                    |
                                    | job_id (FK)                |
                                    | tenant_id (FK)             |
                                    | clause_index               |
                                    | clause_type                |
                                    | risk_level                 |
                                    | summary                    |
                                    | playbook_deviation         |
                                    | suggested_redline          |
                                    | created_at                 |
                                    +----------------------------+




                     +-----------------------------------------+
                     |             VECTOR_STORE                |
                     +-----------------------------------------+
                     | id (PK)                                |
                     | content                                |
                     | metadata (JSONB)                       |
                     | embedding (VECTOR)                     |
                     +-----------------------------------------+
```

---

# Database Design Philosophy

The schema follows several principles.

- Normalized relational data
- Multi-tenant isolation
- Stateless AI workers
- Independent document storage
- AI-friendly vector storage

---

# Tenant Table

```
tenants
```

The Tenant table represents an organization using ClauseIQ.

Example:

```
Google

Microsoft

Amazon

Netflix
```

Each tenant owns:

- Users
- Documents
- Analysis Jobs
- Clause Findings
- Vector Embeddings

No data is shared across tenants.

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | Unique tenant identifier |
| company_name | Organization name |
| plan | Subscription plan |
| created_at | Creation timestamp |

---

# Why Tenant Table?

Instead of creating a separate database for every customer,

ClauseIQ uses

```
Shared Database

↓

Tenant Isolation
```

This significantly reduces infrastructure cost while maintaining logical separation.

---

# Users Table

```
users
```

Stores all platform users.

Each user belongs to exactly one tenant.

```
Tenant

↓

Many Users
```

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | User UUID |
| tenant_id | Organization |
| email | Login email |
| password_hash | BCrypt password |
| role | ADMIN / MEMBER |
| created_at | Account creation |

---

# Password Storage

Passwords are **never stored in plain text**.

Instead

```
Password

↓

BCrypt

↓

Hash

↓

Database
```

This protects user credentials even if the database is compromised.

---

# Role Management

Current roles:

```
ADMIN

MEMBER
```

Future roles may include

```
LEGAL_REVIEWER

AUDITOR

SUPER_ADMIN
```

---

# Documents Table

```
documents
```

Represents uploaded files.

Documents may be:

```
Playbook

Contract
```

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | Document UUID |
| tenant_id | Owner |
| filename | Original filename |
| kind | PLAYBOOK / CONTRACT |
| chunk_count | Number of generated chunks |
| created_at | Upload time |

---

# Why Store Metadata?

The actual document is processed separately.

The database stores:

- Ownership
- Upload history
- Chunk information

instead of repeatedly processing PDFs.

---

# Analysis Jobs Table

```
analysis_jobs
```

Represents one AI analysis execution.

Example:

```
Contract

↓

Analyze

↓

Job Created
```

Every analysis request creates a new job.

---

## Job Lifecycle

```
QUEUED

↓

PROCESSING

↓

COMPLETED
```

If something goes wrong:

```
FAILED
```

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | Job UUID |
| tenant_id | Owner |
| document_id | Contract |
| status | Current Status |
| processed_clauses | Progress |
| total_clauses | Total clauses |
| overall_risk | Final Risk |
| total_tokens | AI Usage |
| error_message | Failure reason |
| created_at | Start time |
| updated_at | Last update |

---

# Why Separate Job Table?

A contract may be analyzed multiple times.

Example:

```
Contract

↓

Version 1

↓

Analyze

↓

Later

↓

Policy Changed

↓

Analyze Again
```

Each execution becomes a separate job.

---

# Clause Findings Table

```
clause_findings
```

Stores the AI output for every clause.

Instead of storing one giant report,

ClauseIQ stores every clause independently.

---

Example

```
Clause 1

↓

LOW RISK

Clause 2

↓

HIGH RISK

Clause 3

↓

MEDIUM RISK
```

This enables:

- Faster queries
- Better filtering
- Easier reporting

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | Finding UUID |
| job_id | Analysis Job |
| tenant_id | Owner |
| clause_index | Clause Number |
| clause_type | Clause Category |
| risk_level | AI Risk |
| summary | Clause Summary |
| playbook_deviation | Policy Difference |
| suggested_redline | AI Recommendation |
| created_at | Timestamp |

---

# Why Store Findings Separately?

Advantages

- Independent clause search
- Better analytics
- Dashboard generation
- Filtering high-risk clauses

---

# Vector Store

The vector store powers Retrieval-Augmented Generation.

```
vector_store
```

Unlike relational tables,

this stores semantic embeddings.

---

## Table Structure

| Column | Description |
|----------|-------------|
| id | Chunk UUID |
| content | Original chunk |
| metadata | JSON metadata |
| embedding | Vector representation |

---

# Metadata Example

```json
{
    "tenantId": "...",
    "documentId": "...",
    "documentType": "PLAYBOOK",
    "chunkNumber": 17
}
```

Metadata allows filtering vectors before similarity search.

---

# Embedding Storage

```
Chunk

↓

Embedding Model

↓

1536 Floating Point Numbers

↓

VECTOR Column
```

Each vector represents the semantic meaning of a document chunk.

---

# Why pgvector?

Using pgvector provides:

- SQL support
- Transaction support
- Vector similarity search
- Simpler deployment
- Native PostgreSQL integration

No external vector database is required.

---

# Similarity Search

When a contract clause arrives

```
Clause

↓

Embedding

↓

Vector Search

↓

Top K Matches
```

Only the nearest playbook chunks are retrieved.

---

# Database Relationships

```
Tenant

↓

Users

↓

Documents

↓

Analysis Jobs

↓

Clause Findings
```

The Vector Store is logically connected through metadata rather than foreign keys.

---

# Indexing Strategy

Proper indexing is critical for performance.

Current indexes include:

```
users(tenant_id)

documents(tenant_id)

analysis_jobs(tenant_id)

clause_findings(job_id)

vector_store(embedding)
```

These indexes improve:

- Tenant filtering
- Report retrieval
- Vector similarity search

---

# HNSW Vector Index

The vector column uses an HNSW (Hierarchical Navigable Small World) index.

```
Embedding

↓

HNSW Index

↓

Fast Approximate Search
```

Compared to linear search,

HNSW dramatically improves similarity search performance on large datasets.

---

# Multi-Tenant Data Isolation

Every business table contains

```
tenant_id
```

Every query includes

```
WHERE tenant_id = ?
```

This guarantees that:

- Tenant A cannot read Tenant B's contracts.
- Tenant B cannot access Tenant A's findings.
- Vector searches remain tenant-specific.

---

# Why Use UUIDs?

All primary keys use UUIDs instead of sequential integers.

Advantages:

- Globally unique identifiers
- Easier distributed systems
- No predictable IDs
- Better suited for microservices

---

# Database Design Principles

The ClauseIQ database follows these principles:

- Relational integrity
- Tenant isolation
- Stateless processing
- AI-friendly storage
- Independent job tracking
- Scalable vector search

By combining traditional relational modeling with vector embeddings, ClauseIQ supports both transactional business operations and advanced semantic AI retrieval within a single PostgreSQL instance.

---

# Phase 6 — Event-Driven Architecture (Apache Kafka + Redis)

One of the primary goals of ClauseIQ is to provide a highly scalable contract analysis platform capable of processing multiple contracts concurrently without blocking users.

Instead of processing AI requests synchronously over HTTP, ClauseIQ follows an **Event-Driven Architecture (EDA)** using **Apache Kafka**.

This design enables:

- High Throughput
- Loose Coupling
- Asynchronous Processing
- Independent Scaling
- Better Fault Tolerance
- Improved User Experience

---

# Why Event-Driven Architecture?

Imagine an AI analysis takes:

```
Contract A

↓

OpenAI

↓

45 Seconds
```

If the client waits for the response:

```
Client

↓

HTTP Request

↓

45 Seconds

↓

Timeout
```

This is not acceptable in production.

Instead ClauseIQ immediately returns:

```
202 Accepted
```

while the processing continues in the background.

---

# Synchronous vs Asynchronous Processing

## Traditional Approach

```
Client

↓

HTTP Request

↓

AI Processing

↓

Database

↓

Response
```

Problems:

- Request blocks
- HTTP Timeout
- Poor scalability
- Bad user experience

---

## ClauseIQ Approach

```
Client

↓

HTTP Request

↓

Create Job

↓

Kafka

↓

202 Accepted

────────────────────────

Kafka

↓

AI Worker

↓

Analysis

↓

Database

↓

Completed
```

The client never waits for AI processing.

---

# Event-Driven Architecture

```
                    Client
                       │
                       ▼
              Contract Service
                       │
             Create Analysis Job
                       │
                       ▼
             Publish Kafka Event
                       │
                       ▼
               analysis-requested
                       │
──────────────────────────────────────────
                       │
                       ▼
                  AI Worker
                       │
             Process Contract
                       │
             Publish Progress
                       │
                       ▼
               analysis-progress
                       │
                       ▼
              Contract Service
                       │
                       ▼
             Stream Progress
```

---

# Why Kafka?

Apache Kafka provides:

- Distributed messaging
- High throughput
- Durable storage
- Message ordering
- Consumer groups
- Horizontal scalability

Kafka allows producers and consumers to operate independently.

---

# Producer and Consumer

```
Producer

↓

Kafka Topic

↓

Consumer
```

The producer does not know:

- Who consumes the message
- When it is consumed
- How many consumers exist

This creates loose coupling.

---

# Kafka Topics

ClauseIQ currently uses two logical events.

```
analysis-requested

analysis-progress
```

---

# analysis-requested

Published by:

```
Contract Service
```

Consumed by:

```
AI Worker
```

Purpose:

```
Start AI Processing
```

---

## Event Flow

```
Analyze Contract

↓

Create Job

↓

Publish Event

↓

Worker Starts
```

---

# AnalysisRequestedEvent

Example

```json
{
    "jobId":"1b4f...",
    "tenantId":"f28d...",
    "documentId":"91ac...",
    "requestedBy":"admin@company.com"
}
```

---

## Why Send IDs Instead of Documents?

Never send large documents through Kafka.

Bad

```
Kafka

↓

Entire PDF
```

Good

```
Kafka

↓

Document ID
```

The AI Worker retrieves the document directly from PostgreSQL.

Advantages:

- Smaller messages
- Faster transmission
- Better reliability
- Lower network usage

---

# analysis-progress

Published by:

```
AI Worker
```

Consumed by:

```
Contract Service
```

Purpose:

```
Live Progress Updates
```

---

Example

```json
{
    "jobId":"1b4f...",
    "status":"PROCESSING",
    "processedClauses":18,
    "totalClauses":47
}
```

---

# Why Progress Events?

AI analysis may take several minutes.

Instead of showing:

```
Loading...
```

the user sees

```
18 / 47 Clauses

↓

24 / 47 Clauses

↓

39 / 47 Clauses

↓

Completed
```

This provides a much better user experience.

---

# Complete Event Flow

```
Client

↓

Analyze Contract

↓

Contract Service

↓

Create Job

↓

Publish Event

↓

Kafka

↓

AI Worker

↓

Read Event

↓

Retrieve Contract

↓

Analyze

↓

Publish Progress

↓

Kafka

↓

Contract Service

↓

Client
```

---

# AI Worker Lifecycle

```
Waiting

↓

Receive Kafka Event

↓

Load Document

↓

Split Clauses

↓

Process Clause

↓

Store Result

↓

Publish Progress

↓

Next Clause

↓

Completed
```

The worker continuously listens for new messages.

---

# Consumer Groups

Kafka Consumer Groups allow multiple workers to process contracts in parallel.

```
                Kafka Topic

                     │

      ┌──────────────┼──────────────┐

      ▼              ▼              ▼

 Worker 1       Worker 2       Worker 3
```

Example:

```
Contract A

↓

Worker 1

Contract B

↓

Worker 2

Contract C

↓

Worker 3
```

Scaling becomes as simple as adding more worker instances.

---

# Horizontal Scaling

Suppose 100 contracts arrive simultaneously.

```
100 Contracts

↓

Kafka Queue

↓

10 AI Workers

↓

Parallel Processing
```

The workload is automatically distributed.

No application code changes are required.

---

# Message Lifecycle

```
Producer

↓

Topic

↓

Partition

↓

Consumer

↓

Processing

↓

Commit Offset
```

Once processing completes successfully,

Kafka commits the message offset.

---

# Message Ordering

Messages inside a partition remain ordered.

```
Message 1

↓

Message 2

↓

Message 3
```

This guarantees predictable processing for a single partition.

---

# Fault Tolerance

Suppose the AI Worker crashes.

```
Worker

↓

Crash
```

The message remains inside Kafka.

When the worker restarts,

```
Kafka

↓

Redeliver Message

↓

Continue Processing
```

No request is lost.

---

# Retry Strategy

If an AI request fails,

```
Message

↓

Retry

↓

Retry

↓

Retry

↓

Failed
```

A production deployment may move permanently failed messages into a **Dead Letter Queue (DLQ)** for later investigation.

---

# Why IDs Instead of Objects?

Good practice:

```
Kafka

↓

Job ID

↓

Document ID

↓

Tenant ID
```

Bad practice:

```
Entire Contract

Entire PDF

Entire User Object
```

Keeping messages lightweight improves performance and reduces serialization overhead.

---

# Redis Integration

Redis complements Kafka by handling temporary and real-time data.

Responsibilities include:

- AI response cache
- Progress cache
- Pub/Sub messaging
- Rate limiting
- Temporary processing state

---

# Redis Cache Flow

```
Clause

↓

Redis

↓

Found?

↓

Yes

↓

Return Cached Result

──────────────

No

↓

Call OpenAI

↓

Save Cache

↓

Return Result
```

Repeated requests avoid unnecessary LLM calls.

---

# Redis Pub/Sub

Progress updates are distributed using Redis Pub/Sub.

```
AI Worker

↓

Redis Publish

↓

Contract Service

↓

SSE

↓

Browser
```

Clients receive updates immediately without polling.

---

# Server-Sent Events (SSE)

Instead of repeatedly asking:

```
Is my job finished?

Is my job finished?

Is my job finished?
```

the client opens one persistent connection.

```
Browser

↓

SSE Connection

↓

Progress Events

↓

Completed
```

This significantly reduces unnecessary HTTP requests.

---

# Complete Messaging Architecture

```
                    Contract Service
                           │
                           ▼
                AnalysisRequestedEvent
                           │
                           ▼
                     Kafka Topic
                 analysis-requested
                           │
                           ▼
                      AI Worker
                           │
        ┌──────────────────┼─────────────────┐
        ▼                  ▼                 ▼
 PostgreSQL          Redis Cache        OpenAI API
        │                  │                 │
        └──────────────────┼─────────────────┘
                           ▼
                 AnalysisProgressEvent
                           │
                           ▼
                     Kafka Topic
                 analysis-progress
                           │
                           ▼
                  Contract Service
                           │
                           ▼
                      Redis Pub/Sub
                           │
                           ▼
                     Browser (SSE)
```

---

# Why Not Call AI Directly?

Without Kafka:

```
Contract Service

↓

OpenAI

↓

Wait

↓

Response
```

Problems:

- Long HTTP requests
- Timeout risk
- Poor scalability
- Tight coupling

With Kafka:

```
Contract Service

↓

Kafka

↓

Worker

↓

OpenAI

↓

Store Results
```

Every component becomes independent.

---

# Benefits of the Event-Driven Design

- Non-blocking APIs
- Independent microservices
- High scalability
- Reliable processing
- Better fault tolerance
- Easy horizontal scaling
- Improved user experience
- Decoupled architecture

---

# Architectural Principles

The messaging architecture follows these principles:

- Asynchronous communication
- Event sourcing mindset
- Loose coupling
- Independent deployment
- Fault isolation
- Horizontal scalability
- Reliable message delivery

By separating request handling from AI processing, ClauseIQ can continue accepting new analysis requests even while existing jobs are still running, making the platform responsive, scalable, and suitable for enterprise workloads.

---
# Phase 7 — API Reference, Local Development & Future Roadmap

This section explains how to run ClauseIQ locally, how to interact with every API, and the future direction of the platform.

---

# API Overview

All client requests enter the system through the **API Gateway**.

```
                 Client
                    │
                    ▼
             API Gateway
                    │
    ┌───────────────┼────────────────┐
    ▼               ▼                ▼
User Service   Ingestion Service   Contract Service
```

Every protected endpoint requires a valid JWT.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Authentication APIs

## Register User

Creates a new tenant and its administrator.

### Endpoint

```
POST /api/v1/auth/register
```

### Request

```json
{
  "companyName": "Acme Corporation",
  "email": "admin@acme.com",
  "password": "Password@123"
}
```

### Response

```json
{
  "message":"User registered successfully"
}
```

---

## Login

Authenticates the user and returns a JWT.

### Endpoint

```
POST /api/v1/auth/login
```

### Request

```json
{
  "email":"admin@acme.com",
  "password":"Password@123"
}
```

### Response

```json
{
    "token":"eyJhbGc....",
    "user":{
        "id":"...",
        "email":"admin@acme.com",
        "role":"ADMIN",
        "tenantId":"..."
    }
}
```

---

# Document Upload APIs

## Upload Playbook

Playbooks define organization-specific legal standards.

### Endpoint

```
POST /api/v1/ingestion/playbook
```

### Request

```
multipart/form-data

file=company-playbook.pdf
```

### Response

```json
{
    "documentId":"ab83...",
    "status":"UPLOADED"
}
```

---

## Upload Contract

Uploads a contract that will later be analyzed.

### Endpoint

```
POST /api/v1/ingestion/contract
```

### Request

```
multipart/form-data

file=vendor-contract.pdf
```

### Response

```json
{
    "documentId":"ce12...",
    "chunks":47
}
```

---

# Analysis APIs

## Start Analysis

Creates a new analysis job.

### Endpoint

```
POST /api/v1/contracts/{documentId}/analyze
```

### Response

```json
{
    "jobId":"91db...",
    "status":"QUEUED"
}
```

HTTP Status

```
202 Accepted
```

The API returns immediately while the AI Worker processes the contract asynchronously.

---

## Check Job Status

### Endpoint

```
GET /api/v1/analysis/{jobId}
```

### Response

```json
{
    "jobId":"...",
    "status":"PROCESSING",
    "processedClauses":18,
    "totalClauses":47
}
```

Possible statuses:

```
QUEUED

PROCESSING

COMPLETED

FAILED
```

---

## Stream Live Progress

Clients can receive real-time updates using Server-Sent Events.

### Endpoint

```
GET /api/v1/analysis/{jobId}/stream
```

Example stream

```
data:
{
  "processedClauses":5,
  "totalClauses":47
}

data:
{
  "processedClauses":17,
  "totalClauses":47
}

data:
{
  "processedClauses":47,
  "totalClauses":47
}
```

---

## Retrieve Final Report

Returns the completed AI analysis.

### Endpoint

```
GET /api/v1/analysis/{jobId}/report
```

Example

```json
{
  "overallRisk":"HIGH",
  "clauses":[
      {
          "clauseType":"Termination",
          "riskLevel":"HIGH",
          "summary":"Notice period exceeds company policy.",
          "playbookDeviation":"Company policy recommends 30 days.",
          "suggestedRedline":"Reduce notice period to 30 days."
      }
  ]
}
```

---

# End-to-End Request Lifecycle

```
User

↓

Register

↓

Login

↓

JWT Generated

↓

Upload Playbook

↓

Playbook Embedded

↓

Upload Contract

↓

Create Analysis Job

↓

Kafka Event

↓

AI Worker

↓

Retrieve Playbook Context

↓

OpenAI Analysis

↓

Save Findings

↓

Progress Updates

↓

Generate Final Report
```

---

# Local Development

## Prerequisites

- Java 21
- Maven 3.9+
- Docker Desktop
- PostgreSQL (pgvector)
- Redis
- Apache Kafka
- OpenAI API Key

---

# Clone Repository

```bash
git clone https://github.com/<your-username>/clauseiq.git

cd clauseiq
```

---

# Start Infrastructure

```bash
docker compose up -d
```

Containers started

```
PostgreSQL

Redis

Kafka
```

Verify

```bash
docker ps
```

---

# Environment Variables

```text
OPENAI_API_KEY=your-api-key

JWT_SECRET=your-secret-key
```

---

# Build Project

```bash
mvn clean install
```

---

# Run Microservices

### API Gateway

```bash
cd api-gateway

mvn spring-boot:run
```

---

### User Service

```bash
cd user-service

mvn spring-boot:run
```

---

### Ingestion Service

```bash
cd ingestion-service

mvn spring-boot:run
```

---

### Contract Service

```bash
cd contract-service

mvn spring-boot:run
```

---

### AI Worker

```bash
cd ai-worker

mvn spring-boot:run
```

---

# Local Ports

| Service | Port |
|----------|------|
| API Gateway | 8080 |
| User Service | 8082 |
| Ingestion Service | 8081 |
| Contract Service | 8083 |
| AI Worker | Background Service |
| PostgreSQL | 5456 |
| Redis | 6379 |
| Kafka | 9092 |

---

# Project Folder Structure

```
clauseiq
│
├── api-gateway
│
├── user-service
│
├── ingestion-service
│
├── contract-service
│
├── ai-worker
│
├── common
│
├── db
│
├── docker-compose.yml
│
└── pom.xml
```

---

# Current Architecture Summary

```
                    Client
                       │
                       ▼
                 API Gateway
                       │
        ┌──────────────┼───────────────┐
        ▼              ▼               ▼
 User Service   Ingestion Service   Contract Service
                                       │
                                       ▼
                                     Kafka
                                       │
                                       ▼
                                  AI Worker
                           ┌─────────┴─────────┐
                           ▼                   ▼
                     PostgreSQL             Redis
                           │
                           ▼
                        OpenAI
```

---

# Engineering Decisions

| Requirement | Solution |
|-------------|----------|
| Authentication | Spring Security + JWT |
| Multi-Tenancy | Tenant-based data isolation |
| API Routing | Spring Cloud Gateway |
| Asynchronous Processing | Apache Kafka |
| AI Integration | Spring AI |
| LLM | OpenAI |
| Semantic Search | pgvector |
| Caching | Redis |
| Progress Updates | Server-Sent Events |
| Persistence | PostgreSQL |
| Build Tool | Maven |
| Containerization | Docker Compose |

---

# Future Enhancements

The current implementation establishes a solid foundation for an enterprise AI platform. Future improvements may include:

### Authentication & Security

- Refresh Tokens
- Spring Authorization Server
- OAuth2 / OpenID Connect
- Keycloak Integration
- Multi-Factor Authentication (MFA)
- Fine-grained Role-Based Access Control (RBAC)

---

### AI Improvements

- Support for multiple LLM providers (OpenAI, Anthropic, Gemini, Azure OpenAI)
- Dynamic Prompt Templates
- AI Evaluation & Feedback Loop
- Automatic Clause Classification
- Contract Comparison
- AI-generated Executive Summaries

---

### Platform Improvements

- Kubernetes Deployment
- Horizontal Auto Scaling
- Distributed Tracing (OpenTelemetry)
- Prometheus Metrics
- Grafana Dashboards
- Centralized Logging (ELK Stack)
- Circuit Breakers (Resilience4j)
- Dead Letter Queue (Kafka DLQ)
- Retry Policies
- CI/CD Pipeline (GitHub Actions)

---

### Storage Improvements

- Amazon S3 / Azure Blob Storage for document storage
- Flyway Database Versioning
- Document Version Management
- Backup & Disaster Recovery

---

# Key Learnings

Building ClauseIQ demonstrates practical implementation of:

- Enterprise Microservices
- Domain-Driven Design
- Event-Driven Architecture
- Distributed Systems
- Spring Boot Ecosystem
- Spring Security
- Spring AI
- Apache Kafka
- Redis
- PostgreSQL
- Vector Databases
- Retrieval-Augmented Generation (RAG)
- Large Language Model Integration
- Docker-based Development
- Multi-Tenant SaaS Architecture

---

# Conclusion

ClauseIQ is more than a document analysis application—it is a complete AI-powered SaaS platform designed using modern enterprise software engineering principles.

The project combines **Spring Boot Microservices**, **Spring AI**, **Apache Kafka**, **Redis**, **PostgreSQL with pgvector**, and **OpenAI** to build a scalable, asynchronous, and multi-tenant contract analysis system.

By separating authentication, document ingestion, orchestration, AI processing, and messaging into dedicated services, the platform achieves loose coupling, independent scalability, and maintainability. Retrieval-Augmented Generation ensures that AI responses are grounded in organization-specific legal policies, enabling accurate, explainable, and context-aware contract reviews.

ClauseIQ serves as a practical demonstration of how modern Java technologies can be integrated to build production-style AI applications that are secure, distributed, and ready to evolve toward enterprise deployment.

---

**Made with ❤️ using Java, Spring Boot, Spring AI, PostgreSQL, Redis, Kafka, OpenAI, and Docker.**