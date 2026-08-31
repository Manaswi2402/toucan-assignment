# Transaction Starter Project

## Problem Understanding

This project implements a small transaction-processing REST service using Java Spring Boot.

The service supports creating transactions, retrieving a transaction by ID, updating transaction status, and retrieving all transactions belonging to a customer.

## Assumptions

- Transaction ID is unique.
- New transactions are expected to start with an initial status such as `PENDING`.
- Once a transaction becomes `COMPLETED` or `FAILED`, its status cannot be changed.
- The application uses the H2 in-memory database provided by the starter project.

## Validation Rules

A transaction is considered valid when:

- Transaction ID is required and must be unique.
- Customer ID is required.
- Amount is required and must be greater than zero.
- Currency is required and must be a 3-letter uppercase code such as `INR`.
- Transaction type is required.
- Transaction status is required.

Business validation:

- A duplicate transaction ID returns HTTP `409 Conflict`.
- A transaction that does not exist returns HTTP `404 Not Found`.
- A status update is allowed only to `COMPLETED` or `FAILED`.
- A `COMPLETED` or `FAILED` transaction cannot be changed again.

## API Endpoints
## Status Transition Rules

- A `PENDING` transaction can be changed to `COMPLETED` or `FAILED`.
- A `COMPLETED` transaction cannot be changed.
- A `FAILED` transaction cannot be changed.
- Status updates are accepted only for `COMPLETED` or `FAILED`.

### 1. Create Transaction

`POST /api/transactions`

Example request:

```json
{
  "transactionId": "TXN001",
  "customerId": "CUS001",
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
```
### 2. Get Transaction

`GET /api/transactions/{transactionId}`

Example:

`GET /api/transactions/TXN001`

### 3. Update Transaction Status

`PATCH /api/transactions/{transactionId}/status?status={status}`

Example:

`PATCH /api/transactions/TXN001/status?status=COMPLETED`

### 4. Get Customer Transactions

`GET /api/transactions/customer/{customerId}`

Example:

`GET /api/transactions/customer/CUS001`

## Testing

Six meaningful tests were added to cover successful transaction creation, invalid transaction amounts, duplicate transaction IDs, and missing transactions.

All four REST operations were also manually tested.

The complete test suite passes using:

`mvnw.cmd clean test`

## Known Limitations

- H2 is an in-memory database, so data is lost when the application restarts.
- Transaction status and transaction type are currently represented as strings.

## Improvements With More Time

- Use a persistent production database.
- Introduce custom exceptions for clearer error handling.
- Add more controller-level integration tests.
- Use enums for transaction status and transaction type.

## AI Usage Disclosure

AI tools were used for development guidance, debugging assistance, code review, and understanding the implementation. The final implementation was reviewed, tested, and verified by the candidate.