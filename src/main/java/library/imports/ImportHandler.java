package library.imports;

import library.objects.Timestamp;
import library.objects.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * The goal of this class is to import transactions from different files
 */
public class ImportHandler {

    public static ArrayList<Transaction> importCiti(String fileName, Connection conn) {

        try {
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
            BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
            List<String> lines = br.lines().collect(Collectors.toList());

            boolean first = true;
            for(String line : lines) {
                if(first) {
                    first = false;
                    continue;
                }

                //Fields are:
                //0- status
                //1- date
                //2- description
                //3- debit
                //4- credit
                line = line.replace("\"", "");
                String[] fields = line.split(",");

                //not pending transaction
                if(fields[0].equals("Cleared")) {
                    Transaction transaction = new Transaction();
                    transaction.setTimestamp(new Timestamp(fields[1] + " 00:00:00"));
                    transaction.setAmount(Double.parseDouble(fields[3]));
                    //TODO: need to add vendor still
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
