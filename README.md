# Webhook SQL Solver (bajaj)

Webhook SQL Solver is a Spring Boot application that automates a common hiring challenge: generating a webhook, preparing an SQL solution using your registration details, and submitting that solution back to the hiring platform. The application integrates with external webhook endpoints, logs each step, and is designed with standard Spring Boot practices so it’s easy to run, extend, and secure.

## Overview

This service orchestrates a simple automation flow:
1. Generate a webhook URL from the hiring platform.
2. Prepare an SQL query/solution using the configured user information (name, email, registration number).
3. Submit the SQL solution back via the provided webhook.

All external endpoints are provided as application properties so the flow can be tested or switched between environments easily.

## Architecture & Flow

- Spring Boot application with Web starter for HTTP clients and potential REST endpoints.
- Configuration-driven URLs for:
  - Webhook generation: `webhook.generate.url`
  - Webhook test/verification: `webhook.test.url`
- A service layer (under the `com.example.webhooksqlsolver` package) handles:
  - Calling external endpoints
  - Preparing payloads (including the SQL solution)
  - Submitting results
- Logging with SLF4J provides step-by-step visibility of requests and responses (sanitized where appropriate).

## Tech Stack

- Java 17
- Spring Boot 3.2.x
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa (optional use)
- H2 Database (runtime; optional in-memory persistence)
- JJWT (JSON Web Token) libraries (included; can be used if token parsing/validation is needed)
- Lombok (optional, for reduced boilerplate)
- Maven (build tool)

Dependencies are declared in `pom.xml`:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- com.h2database:h2 (runtime)
- io.jsonwebtoken:jjwt-* (API, impl, jackson)
- org.projectlombok:lombok

## Data & Persistence

- H2 (in-memory) is included as a runtime dependency via Maven.
- If JPA entities/repositories are present, the app can persist transient data during the flow.
- No external database is required by default; H2 is sufficient for local development and testing.

## Security Considerations

- Do not commit real personal data. Replace `app.user.*` with placeholders and rely on environment variables for real values.
- Keep tokens and credentials out of the code and logs.
- The `.gitignore` is set up to ignore `src/main/resources/application.properties`. Provide a sanitized `application-example.properties` for collaborators.
- If you enable JWT features (jjwt), store signing keys securely and rotate them periodically.

## Troubleshooting

- 4xx/5xx from webhook endpoints:
  - Verify `webhook.generate.url` and `webhook.test.url`.
  - Ensure network access and endpoint availability.
- Application fails to start:
  - Check Java version (must be 17+).
  - Inspect `application.properties` for typos.
- No logs for webhook calls:
  - Increase verbosity: `logging.level.com.example.webhooksqlsolver=DEBUG`.

## Project Structure

High-level layout:

```
bajaj
├─ pom.xml
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com/example/webhooksqlsolver/...   # Services, models, and orchestration logic
│  │  └─ resources
│  │     └─ application.properties             # Do not commit real personal data
└─ .gitignore
```
