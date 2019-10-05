package io.joshatron.budgetlibrary.imports;

import com.joestelmach.natty.Parser;
import io.joshatron.budgetlibrary.dtos.Money;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ImportDAOCiti extends ImportDAO {

    @Override
    protected TransactionImport createTransaction(String[] elements) {
        if(elements.length >= 4 && elements[0].equalsIgnoreCase("Cleared")) {
            Parser parser = new Parser();
            Date date = parser.parse(elements[1]).get(0).getDates().get(0);
            LocalDate timestamp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
