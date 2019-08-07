package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;

public class TransactionDAO {

    public static Transaction createTransaction(Session session, Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateTimestamp(timestamp);
        DAOValidator.validateMoney(amount);
        DAOValidator.validateVendor(vendor);
        DAOValidator.validateAccount(account);

        org.hibernate.Transaction tx = session.beginTransaction();

        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        transaction.setAmount(amount);
        transaction.setVendor(vendor);
        transaction.setAccount(account);
        session.save(transaction);

        tx.commit();

        return transaction;
    }

    public static void updateTransaction(Session session, long transactionId, Timestamp newTimestamp, Money newAmount, Vendor newVendor, Account newAccount) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        if(newVendor != null) {
            DAOValidator.validateVendor(newVendor);
        }
        if(newAccount != null) {
            DAOValidator.validateAccount(newAccount);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Transaction transaction = session.get(Transaction.class, transactionId);
        DAOValidator.validateTransaction(transaction);
        if(newTimestamp != null) {
            transaction.setTimestamp(newTimestamp);
        }
        if(newAmount != null) {
            transaction.setAmount(newAmount);
        }
        if(newVendor != null) {
            transaction.setVendor(newVendor);
        }
        if(newAccount != null) {
            transaction.setAccount(newAccount);
        }

        tx.commit();
    }

    public static void deleteTransaction(Session session, long transactionId) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Transaction transaction = session.get(Transaction.class, transactionId);
        DAOValidator.validateTransaction(transaction);
        session.delete(transaction);

        tx.commit();
    }

    public static void getAllTransactions(Session session) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
    }

    public static void getTransactionsByVendor(Session session, Vendor vendor) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
    }

    public static void getTransactionsByAccount(Session session, Account account) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
    }

    public static void searchTransactionsByTimeRange(Session session, Timestamp start, Timestamp end) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
    }

    public static void searchTransactionsByMoneyRange(Session session, Money min, Money max) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
    }
}
