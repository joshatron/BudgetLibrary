package library.imports;

import library.objects.Transaction;

import java.util.ArrayList;

public interface ImportDAO {

    /*
     * Takes a file and returns a list of transactions to import
     */
    ArrayList<Transaction> getTransactions(String file);
    String getName();
}
