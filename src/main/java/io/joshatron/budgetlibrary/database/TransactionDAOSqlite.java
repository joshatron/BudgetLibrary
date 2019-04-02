package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.dtos.Timestamp;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOSqlite implements TransactionDAO {

    private Connection conn;

    public TransactionDAOSqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Transaction createTransaction(Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException {
        if(timestamp == null || amount == null || vendor == null || !vendor.isValid() || account == null || !account.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TRANSACTION);
        }
        if(!getTransactions(timestamp, timestamp, amount, amount, vendor, account, null).isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.TRANSACTION_EXISTS);
        }

        String insert = "INSERT INTO transactions (timestamp, amount, account, vendor) " +
                "VALUES (?,?,?,?);";

        try {
            //add transaction
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setLong(1, timestamp.getTimestampLong());
            stmt.setInt(2, amount.getAmountInCents());
            stmt.setInt(3, account.getId());
            stmt.setInt(4, vendor.getId());
            stmt.executeUpdate();

            return getTransactions(timestamp, timestamp, amount, amount, vendor, account, null).get(0);
        } catch (SQLException e) {
            throw new BudgetLibraryException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void updateTransaction(int transactionId, Transaction newTransaction) throws BudgetLibraryException {
        if(!newTransaction.isValid() || !transactionExists(transactionId)) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TRANSACTION);
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

    @Override
    public void deleteTransaction(int transactionId) throws BudgetLibraryException {
        if(!transactionExists(transactionId)) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TRANSACTION);
        }

        String delete = "DELETE FROM transactions " +
                "WHERE id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean transactionExists(int transactionId) {
        String search = "SELECT * FROM transactions " +
                "WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(search);
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Transaction> getTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) throws  BudgetLibraryException {
        ArrayList<Transaction> transactions = new ArrayList<>();

        String search = "SELECT * FROM transactions " +
                "WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(search);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {

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
}
