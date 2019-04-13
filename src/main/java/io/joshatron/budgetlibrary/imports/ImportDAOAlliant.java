package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Transaction;

public class ImportDAOAlliant extends ImportDAO {

    @Override
    Transaction createTransaction(String[] elements) {
        return null;
    }

    @Override
    public String getName() {
        return "Alliant";
    }
}
