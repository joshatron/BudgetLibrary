package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.dtos.Timestamp;

import java.sql.*;
import java.util.List;

public class TransactionDAOSqlite implements TransactionDAO {

    private Connection conn;
    private VendorDAO vendorDAO;
    private AccountDAO accountDAO;

    public TransactionDAOSqlite(Connection conn, VendorDAO vendorDAO, AccountDAO accountDAO) {
        this.conn = conn;
        this.vendorDAO = vendorDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public int addTransaction(Transaction transaction) {
        if(transaction.isValid()) {
            //transaction already in database
            int transactionId = getTransactionId(transaction);
            if(transactionId != -1) {
                return transactionId;
            }
            //Add vendor and account if new
            if(transaction.getVendor().getId() == -1) {
                transaction.getVendor().setId(vendorDAO.addVendor(transaction.getVendor()));
            }
            if(transaction.getAccount().getId() == -1) {
                transaction.getAccount().setId(accountDAO.addAccount(transaction.getAccount()));
            }

            String insert = "INSERT INTO transactions (timestamp, amount, account, vendor) " +
                    "VALUES (?,?,?,?);";

            try {
                //add transaction
                PreparedStatement stmt = conn.prepareStatement(insert);
                stmt.setLong(1, transaction.getTimestamp().getTimestampLong());
                stmt.setInt(2, transaction.getAmount().getAmountInCents());
                stmt.setInt(3, transaction.getAccount().getId());
                stmt.setInt(4, transaction.getVendor().getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return getTransactionId(transaction);
        }

        return -1;
    }

    @Override
    public void addTransactions(List<Transaction> transactions) {
        for(Transaction transaction : transactions) {
            addTransaction(transaction);
        }
    }

    @Override
    public void updateTransaction(int transactionId, Transaction newTransaction) {
        if(newTransaction.isValid() && getTransactionFromId(transactionId) != null) {
            if(newTransaction.getVendor().getId() == -1) {
                newTransaction.getVendor().setId(vendorDAO.addVendor(newTransaction.getVendor()));
            }
            if(newTransaction.getAccount().getId() == -1) {
                newTransaction.getAccount().setId(accountDAO.addAccount(newTransaction.getAccount()));
            }

            String update = "UPDATE transactions " +
                    "SET timestamp = ?, amount = ?, account = ?, vendor = ? " +
                    "WHERE id = ?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setLong(1, newTransaction.getTimestamp().getTimestampLong());
                stmt.setInt(2, newTransaction.getAmount().getAmountInCents());
                stmt.setInt(3, newTransaction.getAccount().getId());
                stmt.setInt(4, newTransaction.getVendor().getId());
                stmt.setInt(5, transactionId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTransaction(int transactionId) {
        if(transaction.isValid()) {
            int transactionID = SqliteUtils.getTransactionID(transaction, conn);
            if(transactionID == -1) {
                return;
            }

            String delete = "DELETE FROM transactions " +
                    "WHERE id = ?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(delete);
                stmt.setInt(1, transactionID);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Transaction> searchTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) {
        return null;
    }

    @Override
    public Transaction getTransactionFromId(int transactionId) {
        return null;
    }

    private int getTransactionId(Transaction transaction) {
        return -1;
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
}
