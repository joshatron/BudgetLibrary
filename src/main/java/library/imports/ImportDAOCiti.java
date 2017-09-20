package library.imports;

import library.database.CategoryDAO;
import library.database.TransactionDAO;
import library.database.VendorDAO;
import library.objects.Timestamp;
import library.objects.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportDAOCiti implements ImportDAO {

    private TransactionDAO transactionDAO;
    private VendorDAO vendorDAO;
    private CategoryDAO categoryDAO;

    public ImportDAOCiti(TransactionDAO transactionDAO, VendorDAO vendorDAO, CategoryDAO categoryDAO) {
        this.transactionDAO = transactionDAO;
        this.vendorDAO = vendorDAO;
        this.categoryDAO = categoryDAO;
    }


    /*
     * Importer for Citi bank
     * File is a csv where the first line contains the description
     * and the remaining lines contain each transaction with the following fields:
     * 0- status
     * 1- date
     * 2- description
     * 3- debit
     * 4- credit
     */
    @Override
    public ArrayList<Transaction> getTransactions(String file) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            BufferedReader br = Files.newBufferedReader(Paths.get(file));
            List<String> lines = br.lines().collect(Collectors.toList());

            boolean first = true;
            for(String line : lines) {
                if(first) {
                    first = false;
                    continue;
                }

                line = line.replace("\"", "");
                String[] fields = line.split(",");

                //not pending transaction
                if(fields[0].equals("Cleared")) {
                    Transaction transaction = new Transaction();

                    //date in format MM/DD/YYYY, needs to be YYYY-MM-DD
                    String[] date = fields[1].split("/");
                    transaction.setTimestamp(new Timestamp(date[2] + "-" + date[0] + "-" + date[1]));

                    //need to convert double amount to int in cents
                    transaction.setAmount(Math.round((float)Double.parseDouble(fields[3]) * 100));

                    //TODO: need to add vendor still

                    transactions.add(transaction);
                }
            }

            return transactions;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getName() {
        return "Citi";
    }
}
