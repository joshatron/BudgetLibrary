package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.dtos.Timestamp;

import java.sql.*;
import java.util.ArrayList;
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
    public void addTransaction(Transaction transaction) {
        if(transaction.isValid()) {
            //transaction already in database
            if(SqliteUtils.getTransactionID(transaction, conn) != -1) {
                return;
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

        }
    }

    @Override
    public void addTransactions(List<Transaction> transactions) {
        for(Transaction transaction : transactions) {
            addTransaction(transaction);
        }
    }

    @Override
    public void updateTransaction(int transactionId, Transaction oldTransaction) {

    }

    @Override
    public void deleteTransaction(int transactionId) {

    }

    @Override
    public List<Transaction> searchTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) {
        return null;
    }

    @Override
    public Transaction getTransactionFromId(int transactionId) {
        return null;
    }

    @Override
    public void updateTransaction(Transaction transaction, Transaction oldTransaction) {
        if(transaction.isValid() && oldTransaction.isValid()) {
            int transactionID = SqliteUtils.getTransactionID(oldTransaction, conn);
            if(transactionID == -1) {
                return;
            }

            int vendorID = SqliteUtils.getVendorID(transaction.getVendor().getName(), conn);

            String update = "UPDATE transactions " +
                    "SET timestamp = ?, amount = ?, account = ?, vendor = ? " +
                    "WHERE id = ?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setLong(1, transaction.getTimestamp().getTimestampLong());
                stmt.setInt(2, transaction.getAmount().getAmountInCents());
                stmt.setString(3, transaction.getAccount());
                stmt.setInt(4, vendorID);
                stmt.setInt(5, transactionID);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
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
    public List<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String find = "SELECT timestamp, amount, account, vendors.name as vendor_name " +
                "FROM transactions " +
                "LEFT OUTER JOIN vendors on transactions.vendor = vendors.id;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTimestamp(new Timestamp(rs.getLong("timestamp")));
                transaction.setAmount(new Money(rs.getInt("amount")));
                transaction.setAccount(rs.getString("account"));
                transaction.setVendor(vendorDAO.getVendorFromName(rs.getString("vendor_name")));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsinTimeRange(Timestamp start, Timestamp end) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String find = "SELECT timestamp, amount, account, vendors.name as vendor_name " +
                "FROM transactions " +
                "LEFT OUTER JOIN vendors on transactions.vendor = vendors.id " +
                "WHERE timestamp > ? AND " +
                "timestamp < ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setLong(1, start.getTimestampLong());
            stmt.setLong(2, end.getTimestampLong());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTimestamp(new Timestamp(rs.getLong("timestamp")));
                transaction.setAmount(new Money(rs.getInt("amount")));
                transaction.setAccount(rs.getString("account"));
                transaction.setVendor(vendorDAO.getVendorFromName(rs.getString("vendor_name")));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsForVendor(Vendor vendor) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

        String find = "SELECT timestamp, amount, account, vendors.name as vendor_name " +
                "FROM transactions " +
                "LEFT OUTER JOIN vendors on transactions.vendor = vendors.id " +
                "WHERE vendor = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setInt(1, vendorID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTimestamp(new Timestamp(rs.getLong("timestamp")));
                transaction.setAmount(new Money(rs.getInt("amount")));
                transaction.setAccount(rs.getString("account"));
                transaction.setVendor(vendorDAO.getVendorFromName(rs.getString("vendor_name")));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
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
