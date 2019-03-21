package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.objects.Timestamp;
import io.joshatron.budgetlibrary.objects.Transaction;
import io.joshatron.budgetlibrary.objects.Vendor;

import java.util.List;

public interface TransactionDAO {

    void addTransaction(Transaction transaction);
    void addTransactions(List<Transaction> transactions);
    void updateTransaction(Transaction transaction, Transaction oldTransaction);
    void deleteTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end);
    List<Transaction> getTransactionsForVendor(Vendor vendor);
}
