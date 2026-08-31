# Transaction Starter Project

## Problem Understanding

This project implements a small transaction-processing REST service using Java Spring Boot.

The service supports creating transactions, retrieving a transaction by ID, updating transaction status, and retrieving all transactions belonging to a customer.

## Assumptions

- Transaction ID is unique.
- New transactions are expected to start with an initial status such as `PENDING`.
- A transaction can move from `PENDING` to `COMPLETED` or `FAILED`.
- `COMPLETED` and `FAILED` are treated as final statuses and cannot be changed again.
- The application uses the H2 in-memory database provided by the starter project.

## Validation Rules

A transaction is considered valid when:

- Transaction ID is required and must be unique.
- Customer ID is required.
- Amount is required and must be greater than zero.
- Currency is required and must be a 3-letter uppercase code such as `INR`.
- Transaction type is required.
- Transaction status is required.

Additional business validation:

- Supported transaction types are `PAYMENT` and `REFUND`.
- A new transaction must have `PENDING` status.
- A duplicate Transaction ID is rejected with HTTP `409 Conflict`.
- A transaction that does not exist returns HTTP `404 Not Found`.

## API Endpoints
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

## Status Transition Rules

- A PENDING transaction can be changed to COMPLETED or FAILED.
- A COMPLETED transaction cannot be changed.
- A FAILED transaction cannot be changed.
- Status updates are accepted only for COMPLETED or FAILED.


## Testing

Six meaningful tests were added covering:

- Successful transaction creation
- Invalid transaction amount
- Duplicate transaction ID
- Transaction not found
- Successful transaction status update
- Customer transaction lookup

All four REST operations were also manually tested using Postman.

The complete test suite passes using:

`mvnw.cmd clean test`

Final test result:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Known Limitations

- H2 is an in-memory database, so data is lost when the application restarts.
- Transaction status and transaction type are currently represented as strings.

## Improvements With More Time

- Use a persistent production database.
- Add more detailed validation and structured error responses.
- Add more controller-level integration tests.
- Use enums for transaction status and transaction type.