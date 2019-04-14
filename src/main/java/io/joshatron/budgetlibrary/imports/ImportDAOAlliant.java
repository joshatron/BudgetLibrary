package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Timestamp;

public class ImportDAOAlliant extends ImportDAO {

    @Override
    TransactionImport createTransaction(String[] elements) {
        if(elements.length == 4) {
            return new TransactionImport(new Timestamp(elements[0]), new Money(elements[2]), elements[1], getName());
        }

        return null;
    }

    @Override
    public String getName() {
        return "Alliant";
    }
}
