package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Timestamp;
import io.joshatron.budgetlibrary.dtos.Transaction;
import io.joshatron.budgetlibrary.dtos.Vendor;

import java.util.List;

public interface TransactionDAO {

    void addTransaction(Transaction transaction);
    void addTransactions(List<Transaction> transactions);
    void updateTransaction(int transactionId, Transaction oldTransaction);
    void deleteTransaction(int transactionId);

    //TODO: create search transactions
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end);
    List<Transaction> getTransactionsForVendor(Vendor vendor);
}
