package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;

import java.util.List;

public interface TransactionDAO {

    int addTransaction(Transaction transaction);
    void addTransactions(List<Transaction> transactions);
    void updateTransaction(int transactionId, Transaction newTransaction);
    void deleteTransaction(int transactionId);

    List<Transaction> searchTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type);
    Transaction getTransactionFromId(int transactionId);
}
