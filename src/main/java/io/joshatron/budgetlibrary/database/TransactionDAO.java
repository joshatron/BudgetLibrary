package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

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

    public static List<Transaction> getAllTransactions(Session session) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        Query<Transaction> query = session.createQuery("from Transaction", Transaction.class);
        return query.list();
    }

    public static List<Transaction> getTransactionsByVendor(Session session, Vendor vendor) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateVendor(vendor);

        Query<Transaction> query = session.createQuery("from Transaction t where t.vendor=:vendor", Transaction.class);
        query.setParameter("vendor", vendor);

        return query.list();
    }

    public static List<Transaction> getTransactionsByType(Session session, Type type) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateType(type);

        Query<Transaction> query = session.createQuery("select t from Transaction t inner join Vendor as v where v.type=:type", Transaction.class);
        query.setParameter("type", type);

        return query.list();
    }

    public static List<Transaction> getTransactionsByAccount(Session session, Account account) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateAccount(account);

        Query<Transaction> query = session.createQuery("from Transaction t where t.account=:account", Transaction.class);
        query.setParameter("account", account);

        return query.list();
    }

    public static List<Transaction> searchTransactions(Session session, Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        if(vendor != null) {
            DAOValidator.validateVendor(vendor);
        }
        if(account != null) {
            DAOValidator.validateAccount(account);
        }
        if(vendor != null && type != null) {
            throw new BudgetLibraryException(ErrorCode.CONFLICTING_TYPES);
        }

        boolean first = true;
        StringBuilder q = new StringBuilder();
        if(type != null) {
            q.append("select t from Transaction t inner join Vendor as v");
        }
        else {
            q.append("from Transaction t");
        }
        if(start != null || end != null || min != null || max != null || vendor != null || account != null || type != null) {
            q.append(" where");
        }

        if(start != null && end != null) {
            first = false;
            q.append(" (t.timestamp>=:start and t.timestamp<=:end)");
        }
        else if(start != null) {
            first = false;
            q.append(" t.timestamp>=:start");
        }
        else if(end != null) {
            first = false;
            q.append(" t.timestamp<=:end");
        }

        if(!first && (min != null || max != null)) {
            q.append(" or");
        }
        if(min != null && max != null) {
            first = false;
            q.append(" (t.amount>=:min and t.amount <=:max)");
        }
        else if(min != null) {
            first = false;
            q.append(" t.amount>=:min");
        }
        else if(max != null) {
            first = false;
            q.append(" t.amount<=:max");
        }

        if(vendor != null) {
            if(!first) {
                q.append(" or");
            }
            first = false;
            q.append(" t.vendor=:vendor");
        }

        if(account != null) {
            if(!first) {
                q.append(" or");
            }
            first = false;
            q.append(" t.account=:account");
        }

        if(type != null) {
            if(!first) {
                q.append(" or");
            }
            q.append(" v.type=:type");
        }

        Query<Transaction> query = session.createQuery(q.toString(), Transaction.class);
        if(start != null) {
            query.setParameter("start", start);
        }
        if(end != null) {
            query.setParameter("end", end);
        }
        if(min != null) {
            query.setParameter("min", min);
        }
        if(max != null) {
            query.setParameter("max", max);
        }
        if(vendor != null) {
            query.setParameter("vendor", vendor);
        }
        if(account != null) {
            query.setParameter("account", account);
        }
        if(type != null) {
            query.setParameter("type", type);
        }

        return query.list();
    }

    public static List<Transaction> searchTransactionsByTimeRange(Session session, Timestamp start, Timestamp end) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        StringBuilder q = new StringBuilder();
        q.append("from Transaction t");

        if(start != null || end != null) {
            q.append(" where");
            if(start != null) {
                q.append(" t.timestamp>=:start");
            }
            if(end != null) {
                if(start != null) {
                    q.append(" and ");
                }
                q.append(" t.timestamp<=:end");
            }
        }

        Query<Transaction> query = session.createQuery(q.toString(), Transaction.class);
        if(start != null) {
            query.setParameter("start", start);
        }
        if(end != null) {
            query.setParameter("end", end);
        }

        return query.list();
    }

    public static List<Transaction> searchTransactionsByMoneyRange(Session session, Money min, Money max) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        StringBuilder q = new StringBuilder();
        q.append("from Transaction t");

        if(min != null || max != null) {
            q.append(" where");
            if(min != null) {
                q.append(" t.amount>=:min");
            }
            if(max != null) {
                if(min != null) {
                    q.append(" and ");
                }
                q.append(" t.amount<=:max");
            }
        }

        Query<Transaction> query = session.createQuery(q.toString(), Transaction.class);
        if(min != null) {
            query.setParameter("min", min);
        }
        if(max != null) {
            query.setParameter("max", max);
        }

        return query.list();
    }
}
