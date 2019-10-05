package io.joshatron.budgetlibrary.imports;

import com.joestelmach.natty.Parser;
import io.joshatron.budgetlibrary.dtos.Money;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ImportDAOAlliant extends ImportDAO {

    @Override
    protected TransactionImport createTransaction(String[] elements) {
        if(elements.length == 4) {
            Parser parser = new Parser();
            Date date = parser.parse(elements[0]).get(0).getDates().get(0);
            LocalDate day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return new TransactionImport(day, new Money(elements[2]), elements[1]);
        }

        return null;
    }

    @Override
    public String getName() {
        return "Alliant";
    }
}
