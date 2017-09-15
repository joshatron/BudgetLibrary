package library.operations;

import library.database.DatabaseManager;
import library.database.InsertHandler;
import library.objects.Category;
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
        return InsertHandler.addTransaction(transaction, conn);
    }

    public boolean addTransactions(Transaction[] transactions) {
        for(Transaction transaction : transactions) {

            if(!InsertHandler.addTransaction(transaction, conn)) {
                return false;
            }
        }

        return true;
    }

    public boolean addVendor(Vendor vendor) {
        return InsertHandler.addVendor(vendor, conn);
    }

    public boolean addVendors(Vendor[] vendors) {
        for(Vendor vendor : vendors) {
            if(!InsertHandler.addVendor(vendor, conn)) {
                return false;
            }
        }

        return true;
    }

    public boolean addCategory(Category category) {
        return InsertHandler.addCategory(category, conn);
    }

    public boolean addCategories(Category[] categories) {
        for(Category category : categories) {
            if(!InsertHandler.addCategory(category, conn)) {
                return false;
            }
        }

        return true;
    }
}
