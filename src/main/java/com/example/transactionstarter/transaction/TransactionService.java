package com.example.transactionstarter.transaction;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {

        // Check for duplicate transaction ID
        if (transactionRepository.existsById(transaction.getTransactionId())) {
            throw new DuplicateTransactionException(
                    "Transaction ID already exists");
        }

        // Allow only supported transaction types
        if (!"PAYMENT".equals(transaction.getTransactionType())
                && !"REFUND".equals(transaction.getTransactionType())) {

            throw new IllegalArgumentException(
                    "Transaction type must be PAYMENT or REFUND");
        }

        // New transactions must start as PENDING
        if (!"PENDING".equals(transaction.getTransactionStatus())) {
            throw new IllegalArgumentException(
                    "New transaction status must be PENDING");
        }

        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found"));
    }

    public Transaction updateStatus(String transactionId, String status) {

        Transaction transaction = getTransaction(transactionId);

        String currentStatus = transaction.getTransactionStatus();

        // COMPLETED and FAILED are final states
        if ("COMPLETED".equals(currentStatus)
                || "FAILED".equals(currentStatus)) {

            throw new IllegalArgumentException(
                    "Transaction status cannot be changed after completion or failure");
        }

        // Only these status changes are allowed
        if (!"COMPLETED".equals(status)
                && !"FAILED".equals(status)) {

            throw new IllegalArgumentException(
                    "Status can only be COMPLETED or FAILED");
        }

        transaction.setTransactionStatus(status);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getCustomerTransactions(String customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }
}