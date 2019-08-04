package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AccountDAO {

    public AccountDAO() throws BudgetLibraryException {
        throw new BudgetLibraryException(ErrorCode.INVALID_INITIALIZATION);
    }

    public static Account createAccount(Session session, String name, String bank, String description) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty() || bank == null || bank.isEmpty() || description == null || description.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = new Account();
        account.setName(name);
        account.setBank(bank);
        account.setDescription(description);
        session.save(account);

        tx.commit();

        return account;
    }

    public static void updateAccount(Session session, long accountId, String name, String bank, String description) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        if(account == null) {
            throw new BudgetLibraryException(ErrorCode.INVALID_ACCOUNT);
        }

        if(name != null) {
            account.setName(name);
        }
        if(bank != null) {
            account.setBank(bank);
        }
        if(description != null) {
            account.setDescription(description);
        }

        tx.commit();
    }

    public static void deleteAccount(Session session, long accountId) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        if(account == null) {
            throw new BudgetLibraryException(ErrorCode.INVALID_ACCOUNT);
        }

        session.delete(account);

        tx.commit();

    }

    public static List<Account> getAllAccounts(Session session) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

        Query<Account> query = session.createQuery("from Account", Account.class);
        return query.list();
    }

    public static Account getAccountByName(Session session, String name) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Account> query = session.createQuery("from Account a where a.name=:name", Account.class);
        query.setParameter("name", name);
        List<Account> accounts = query.list();

        if(accounts.size() == 1) {
            return accounts.get(0);
        }
        else if(accounts.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.NO_RESULT_FOUND);
        }
        else {
            throw new BudgetLibraryException(ErrorCode.TOO_MANY_RESULTS_FOUND);
        }
    }

    public static List<Account> getAccountsByBank(Session session, String bank) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(bank == null || bank.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Account> query = session.createQuery("from Account a where a.bank=:bank", Account.class);
        query.setParameter("bank", bank);

        return query.list();
    }

    public static List<Account> searchAccountsByName(Session session, String name) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Account> query = session.createQuery("from Account a where a.name like :name", Account.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }

    public static List<Account> searchAccountsByBank(Session session, String bank) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(bank == null || bank.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Account> query = session.createQuery("from Account a where a.bank like :bank", Account.class);
        query.setParameter("bank", "%" + bank + "%");

        return query.list();
    }
}
