package library.database;

import library.objects.Category;
import library.objects.Timestamp;
import library.objects.Transaction;
import library.objects.Vendor;

import java.util.ArrayList;

public interface TransactionDAO {

    void addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction, Transaction oldTransaction);
    void deleteTransaction(Transaction transaction);

    ArrayList<Transaction> getAllTransactions();
    ArrayList<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end);
    ArrayList<Transaction> getTransactionsForVendor(Vendor vendor);
    ArrayList<Transaction> getTransactionsForCategory(Category category);
}
