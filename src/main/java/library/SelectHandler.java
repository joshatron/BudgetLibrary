package library;

import java.sql.Connection;
import java.util.ArrayList;

/*
 * Class to handle retrieving data from the database
 */
public class SelectHandler {

    public static ArrayList<Transaction> getTransactions(Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    public  static ArrayList<Transaction> getTransactionsInTimeRange(Timestamp start, Timestamp end, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    public static ArrayList<Transaction> getTransactionsForVendor(Vendor vendor, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    public static ArrayList<Transaction> getTransactionsForCategory(Category category, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    public static ArrayList<Vendor> getVendors(Connection conn) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        return vendors;
    }

    public static ArrayList<Vendor> getVendorsForCategory(Category category, Connection conn) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        return vendors;
    }

    public static ArrayList<Category> getCategories(Connection conn) {
        ArrayList<Category> categories = new ArrayList<Category>();

        return categories;
    }
}
