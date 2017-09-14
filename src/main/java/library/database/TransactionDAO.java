package library.database;

import library.objects.Category;
import library.objects.Timestamp;
import library.objects.Transaction;
import library.objects.Vendor;

import java.util.ArrayList;

public interface TransactionDAO {

    public void addTransaction(Transaction transaction);
    public void updateTransaction(Transaction transaction);
    public void deleteTransaction(Transaction transaction);

    public ArrayList<Transaction> getAllTransactions();
    public ArrayList<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end);
    public ArrayList<Transaction> getTrnsactionsForVendor(Vendor vendor);
    public ArrayList<Transaction> getTransactionsForCategory(Category category);
}
