package library;

import library.database.*;
import library.imports.ImportDAO;
import library.imports.ImportDAOCiti;
import library.objects.Transaction;

import java.sql.Connection;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Connection conn = DatabaseManager.getConnection();
        VendorDAO vendorDAO = new VendorDAOSqlite(conn);
        TransactionDAO transactionDAO = new TransactionDAOSqlite(conn, vendorDAO);
        ImportDAO citi = new ImportDAOCiti(transactionDAO, vendorDAO);

        System.out.println("starting citi");
        ArrayList<Transaction> transactions = citi.getTransactions("citi_last_month.csv");
        System.out.println("ending citi");
        System.out.println(transactions.size());
        for(Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
