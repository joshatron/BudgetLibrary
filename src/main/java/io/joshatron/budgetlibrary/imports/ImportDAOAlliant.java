package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Timestamp;
import io.joshatron.budgetlibrary.dtos.Transaction;

import java.util.List;

public class ImportDAOAlliant implements ImportDAO {

    private TransactionDAO transactionDAO;
    private VendorDAO vendorDAO;

    public ImportDAOAlliant(TransactionDAO transactionDAO, VendorDAO vendorDAO) {
        this.transactionDAO = transactionDAO;
        this.vendorDAO = vendorDAO;
    }

    @Override
    public void addTransactions(String file) {
        List<String> lines = ImportUtils.getLines(file);

        for(String line : lines) {
            Transaction transaction = new Transaction();

            String[] fields = line.split("\"");

            String date = fields[0].substring(0, fields[0].length() - 1);
            String vendor = fields[1];
            String amount = fields[2].split(",")[1];

            String[] dateElements = date.split("/");
            transaction.setTimestamp(new Timestamp(dateElements[2] + "-" + dateElements[0] + "-" + dateElements[1]));

            transaction.setVendor(ImportUtils.getVendorFromRaw(vendor, vendorDAO));

            transaction.setAccount(getName());

            boolean isNegative = amount.charAt(0) == '-';
            if(isNegative) {
                amount = "-" + amount.substring(2);
            }
            else {
                amount = amount.substring(1);
            }
            transaction.setAmount(new Money(Double.parseDouble(amount)));

            transactionDAO.addTransaction(transaction);
        }
    }

    @Override
    public String getName() {
        return "alliant";
    }
}
