package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.objects.Timestamp;
import io.joshatron.budgetlibrary.objects.Transaction;
import io.joshatron.budgetlibrary.objects.Vendor;

import java.util.ArrayList;
import java.util.List;

public interface TransactionDAO {

    void addTransaction(Transaction transaction);
    void addTransactions(List<Transaction> transactions);
    void updateTransaction(Transaction transaction, Transaction oldTransaction);
    void deleteTransaction(Transaction transaction);

    ArrayList<Transaction> getAllTransactions();
    ArrayList<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end);
    ArrayList<Transaction> getTransactionsForVendor(Vendor vendor);
}