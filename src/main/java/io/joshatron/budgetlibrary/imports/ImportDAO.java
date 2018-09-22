package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.objects.Transaction;

import java.util.ArrayList;

public interface ImportDAO {

    /*
     * Takes a file and returns a list of transactions to import
     */
    void addTransactions(String file);
    String getName();
}
