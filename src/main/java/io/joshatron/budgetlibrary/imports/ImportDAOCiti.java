package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.objects.Money;
import io.joshatron.budgetlibrary.objects.Timestamp;
import io.joshatron.budgetlibrary.objects.Transaction;
import io.joshatron.budgetlibrary.objects.Vendor;

import java.util.List;

public class ImportDAOCiti implements ImportDAO {

    private TransactionDAO transactionDAO;
    private VendorDAO vendorDAO;

    public ImportDAOCiti(TransactionDAO transactionDAO, VendorDAO vendorDAO) {
        this.transactionDAO = transactionDAO;
        this.vendorDAO = vendorDAO;
    }


    /*
     * Importer for Citi bank
     * File is a csv where the first line contains the description
     * and the remaining lines contain each transaction with the following fields:
     * 0- status
     * 1- date
     * 2- description
     * 3- amount lost
     * 4- amount gained
     */
    @Override
    public void addTransactions(String file) {
        List<String> lines = ImportUtils.getLines(file);

        boolean first = true;
        String fullLine = "";
        for(String line : lines) {
            if(first) {
                first = false;
                continue;
            }

            if(line.charAt(line.length() - 1) == '"') {
                fullLine += line;

                fullLine = fullLine.replace("'", "''");
                String[] fields = fullLine.split("\"");

                //not pending transaction
                if (fields[1].equals("Cleared")) {
                    Transaction transaction = new Transaction();

                    //date in format MM/DD/YYYY, needs to be YYYY-MM-DD
                    String[] date = fields[3].split("/");
                    transaction.setTimestamp(new Timestamp(date[2] + "-" + date[0] + "-" + date[1]));

                    //need to convert double amount to int in cents
                    if(fields[7].length() != 0) {
                        String amount = fields[7].replace(",", "");
                        transaction.setAmount(new Money(Double.parseDouble(amount)));
                    }
                    else {
                        String amount = fields[9].replace(",", "");
                        transaction.setAmount(new Money(Double.parseDouble(amount) * -1));
                    }

                    if(fields[5].charAt(fields[5].length() - 1) == ' ') {
                        fields[5] = fields[5].substring(0, fields[5].length() - 1);
                    }

                    //get vendor from raw name
                    transaction.setVendor(ImportUtils.getVendorFromRaw(fields[5], vendorDAO));

                    transaction.setAccount(getName());

                    transactionDAO.addTransaction(transaction);
                }

                fullLine = "";
            }
            else {
                fullLine += line;
            }
        }
    }

    @Override
    public String getName() {
        return "citi";
    }
}
