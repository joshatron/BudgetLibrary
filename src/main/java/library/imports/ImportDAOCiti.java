package library.imports;

import library.database.TransactionDAO;
import library.database.VendorDAO;
import library.objects.Timestamp;
import library.objects.Transaction;
import library.objects.Vendor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    public ArrayList<Transaction> getTransactions(String file) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            BufferedReader br = Files.newBufferedReader(Paths.get(file));
            List<String> lines = br.lines().collect(Collectors.toList());
            //in case a vendor is not filled out
            Scanner in = new Scanner(System.in);

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
                            transaction.setAmount(Math.round((float) Double.parseDouble(amount) * 100));
                        }
                        else {
                            String amount = fields[9].replace(",", "");
                            transaction.setAmount(Math.round((float) Double.parseDouble(amount) * -100));
                        }

                        if(fields[5].charAt(fields[5].length() - 1) == ' ') {
                            fields[5] = fields[5].substring(0, fields[5].length() - 1);
                        }

                        //get vendor from raw name
                        Vendor vendor = vendorDAO.getVendorFromRaw(fields[5]);
                        //if can't find vendor from raw name
                        if (vendor == null) {
                            System.out.print("What is the vendor for " + fields[5] + "? ");
                            String vendorName = in.nextLine();
                            vendorDAO.addVendorRawMapping(vendorName, fields[5]);
                            vendor = vendorDAO.getVendorFromName(vendorName);

                            if (vendor == null) {
                                vendor = new Vendor(vendorName, null);
                            }
                        }
                        transaction.setVendor(vendor);

                        transactions.add(transaction);
                    }

                    fullLine = "";
                }
                else {
                    fullLine += line;
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
