package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Timestamp;

public class ImportDAOCiti extends ImportDAO {

    @Override
    protected TransactionImport createTransaction(String[] elements) {
        if(elements.length >= 4 && elements[0].equalsIgnoreCase("Cleared")) {
            Timestamp timestamp = new Timestamp(elements[1]);
            Money amount;
            if(!elements[3].isEmpty()) {
                amount = new Money(elements[3]);
                amount.reverseSign();
            }
            else {
                amount = new Money(elements[4]);
                amount.reverseSign();
            }
            return new TransactionImport(timestamp, amount, elements[2]);
        }

        return null;
    }

    @Override
    protected int getInitialLinesSkipped() {
        return 1;
    }

    @Override
    public String getName() {
        return "Citi";
    }
}
