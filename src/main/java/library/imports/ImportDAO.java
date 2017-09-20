package library.imports;

import library.objects.Transaction;

import java.util.ArrayList;

public interface ImportDAO {

    ArrayList<Transaction> getTransactions(String file);
    String getName();
}
