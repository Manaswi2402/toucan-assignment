# AI Usage Disclosure

## Tools Used

I used an AI coding assistant during the development of this assignment.

## How AI Was Used

AI assistance was used to:

- Understand the assignment requirements.
- Plan the project structure and implementation approach.
- Get guidance on Spring Boot, JPA, validation, REST APIs, and JUnit testing.
- Review and improve parts of the Java code.
- Troubleshoot errors encountered while running the application and tests.
- Understand and test the REST APIs using PowerShell and Postman.
- Improve the project documentation.

## AI-Suggested Implementation

AI provided suggestions and guidance for parts of the transaction entity, service-layer business logic, REST controller, exception handling, automated tests, and documentation.

## Changes and Corrections Made

I reviewed and adapted the suggested code to the provided Spring Boot starter project.

During development, I identified and corrected issues through testing, including duplicate transaction handling, exception handling, test expectations, and project configuration issues.

## Verification

I tested the application by running the complete Maven test suite and manually testing all four REST APIs.

The final automated test result was:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```
## Manual API Testing

I also manually tested the REST APIs using Postman and PowerShell, including:

- Successful transaction creation → `201 Created`
- Invalid transaction amount → `400 Bad Request`
- Non-existent transaction → `404 Not Found`
- Duplicate Transaction ID → `409 Conflict`
- Successful transaction status update → `200 OK`

## My Contribution and AI Assistance

AI assistance was used as a supporting resource during the development process for understanding requirements, implementation guidance, code review, and troubleshooting.

I was responsible for understanding the requirements, applying the changes to the starter project, making the final implementation decisions, running the application, testing the APIs, resolving issues, and verifying the final implementation.

## Understanding of the Final Implementation

I understand the code submitted in this repository and can explain the main classes, REST endpoints, validation rules, exception handling, database interaction, status transition logic, and automated tests.

I have reviewed and tested the final implementation and understand the decisions made in the submitted solution.