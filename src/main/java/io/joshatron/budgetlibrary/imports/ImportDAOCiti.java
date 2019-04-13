package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Transaction;

public class ImportDAOCiti extends ImportDAO {

    @Override
    Transaction createTransaction(String[] elements) {
        return null;
    }

    @Override
    public int getInitialLinesSkipped() {
        return 1;
    }

    @Override
    public String getName() {
        return "Citi";
    }
}
