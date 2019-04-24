package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;

import java.util.List;

public class TransactionDAO {

    public static Transaction createTransaction(Session session, Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException {
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

    public static void updateTransaction(Session session, int transactionId, Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Transaction transaction = session.get(Transaction.class, transactionId);
        if(timestamp != null) {
            transaction.setTimestamp(timestamp);
        }
        if(amount != null) {
            transaction.setAmount(amount);
        }
        if(vendor != null) {
            transaction.setVendor(vendor);
        }
        if(account != null) {
            transaction.setAccount(account);
        }

        tx.commit();
    }

    public static void deleteTransaction(Session session, int transactionId) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Transaction transaction = session.get(Transaction.class, transactionId);
        session.delete(transaction);

        tx.commit();
    }

    public static List<Transaction> getTransactions(Session session, Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) throws BudgetLibraryException {
        return null;
    }
}
