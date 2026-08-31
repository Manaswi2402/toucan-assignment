package com.example.transactionstarter;

import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionStarterApplicationTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TEST001",
                "CUST001",
                new BigDecimal("1000.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction saved = transactionService.createTransaction(transaction);

        assertNotNull(saved);
        assertEquals("TEST001", saved.getTransactionId());
        assertEquals("CUST001", saved.getCustomerId());
        assertEquals(new BigDecimal("1000.00"), saved.getAmount());
    }

    @Test
    void shouldRejectInvalidTransaction() {

        Transaction transaction = new Transaction(
                "TEST002",
                "CUST002",
                new BigDecimal("-100.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(Exception.class, () -> {
            transactionRepository.saveAndFlush(transaction);
        });
    }

    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction firstTransaction = new Transaction(
                "TEST003",
                "CUST003",
                new BigDecimal("500.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(firstTransaction);

        Transaction duplicateTransaction = new Transaction(
                "TEST003",
                "CUST004",
                new BigDecimal("700.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(DuplicateTransactionException.class, () -> {
            transactionService.createTransaction(duplicateTransaction);
        });
    }

    @Test
    void shouldRejectTransactionThatDoesNotExist() {

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransaction("DOES_NOT_EXIST");
        });
    }

    @Test
    void shouldUpdateTransactionStatusSuccessfully() {

        Transaction transaction = new Transaction(
                "TEST005",
                "CUST005",
                new BigDecimal("800.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        transactionService.createTransaction(transaction);

        Transaction updated =
                transactionService.updateStatus("TEST005", "COMPLETED");

        assertEquals("COMPLETED", updated.getTransactionStatus());
    }

    @Test
    void shouldGetCustomerTransactions() {

        Transaction first = new Transaction(
                "TEST006",
                "CUST006",
                new BigDecimal("100.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction second = new Transaction(
                "TEST007",
                "CUST006",
                new BigDecimal("200.00"),
                "INR",
                "REFUND",
                "PENDING"
        );

        transactionService.createTransaction(first);
        transactionService.createTransaction(second);

        List<Transaction> transactions =
                transactionService.getCustomerTransactions("CUST006");

        assertEquals(2, transactions.size());
    }
}