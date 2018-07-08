package library.operations;

import library.database.DatabaseManager;
import library.objects.Transaction;
import library.objects.Vendor;

import java.sql.Connection;

/*
 * Wrapper for all the budget classes
 * The goal of this class is to be the univeral api
 */
public class BudgetLibrary {

    private Connection conn;

    public BudgetLibrary() {
        this.conn = DatabaseManager.getConnection();
    }

    public boolean addTransaction(Transaction transaction) {
        return false;
    }

    public boolean addTransactions(Transaction[] transactions) {
        return false;
    }

    public boolean addVendor(Vendor vendor) {
        return false;
    }

    public boolean addVendors(Vendor[] vendors) {
        return false;
    }
}
