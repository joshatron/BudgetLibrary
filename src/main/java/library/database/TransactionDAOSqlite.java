package library.database;

import library.objects.Category;
import library.objects.Timestamp;
import library.objects.Transaction;
import library.objects.Vendor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionDAOSqlite implements TransactionDAO {

    private Connection conn;
    private VendorDAO vendorDAO;
    private CategoryDAO categoryDAO;

    public TransactionDAOSqlite(Connection conn, VendorDAO vendorDAO, CategoryDAO categoryDAO) {
        this.conn = conn;
        this.vendorDAO = vendorDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (transaction.isValid()) {
            //transaction already in database
            if (SqliteUtils.getTransactionID(transaction, conn) != -1) {
                return;
            }
            int vendorID = SelectHandler.getVendorID(transaction.getVendor(), conn);
            //if vendor doesn't exist, add it
            if (vendorID == -1) {
                vendorDAO.addVendor(transaction.getVendor());
                vendorID = SelectHandler.getVendorID(transaction.getVendor(), conn);
            }

            String insert = "INSERT INTO transactions (timestamp, amount, vendor) " +
                    "VALUES ( " + transaction.getTimestamp().getTimestampLong() + ", " +
                    transaction.getAmount() + ", " +
                    vendorID + " );";

            try {
                //add transaction
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }

    @Override
    public void deleteTransaction(Transaction transaction) {

    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTrnsactionsForVendor(Vendor vendor) {
        return null;
    }

    @Override
    public ArrayList<Transaction> getTransactionsForCategory(Category category) {
        return null;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public VendorDAO getVendorDAO() {
        return vendorDAO;
    }

    public void setVendorDAO(VendorDAO vendorDAO) {
        this.vendorDAO = vendorDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
}
