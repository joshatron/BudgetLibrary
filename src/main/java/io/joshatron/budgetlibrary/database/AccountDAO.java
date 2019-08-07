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
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);
        DAOValidator.validateString(bank);
        DAOValidator.validateString(description);

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = new Account();
        account.setName(name);
        account.setBank(bank);
        account.setDescription(description);
        session.save(account);

        tx.commit();

        return account;
    }

    public static void updateAccount(Session session, long accountId, String newName, String newBank, String newDescription) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        if(newName != null) {
            DAOValidator.validateString(newName);
        }
        if(newBank != null) {
            DAOValidator.validateString(newBank);
        }
        if(newDescription != null) {
            DAOValidator.validateString(newDescription);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        DAOValidator.validateAccount(account);

        if(newName != null) {
            account.setName(newName);
        }
        if(newBank != null) {
            account.setBank(newBank);
        }
        if(newDescription != null) {
            account.setDescription(newDescription);
        }

        tx.commit();
    }

    public static void deleteAccount(Session session, long accountId) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        DAOValidator.validateAccount(account);

        session.delete(account);

        tx.commit();

    }

    public static List<Account> getAllAccounts(Session session) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        Query<Account> query = session.createQuery("from Account", Account.class);
        return query.list();
    }

    public static Account getAccountByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Account> query = session.createQuery("from Account a where a.name=:name", Account.class);
        query.setParameter("name", name);
        List<Account> accounts = query.list();

        DAOValidator.validateOnlyOneResult(accounts);
        return accounts.get(0);
    }

    public static List<Account> getAccountsByBank(Session session, String bank) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(bank);

        Query<Account> query = session.createQuery("from Account a where a.bank=:bank", Account.class);
        query.setParameter("bank", bank);

        return query.list();
    }

    public static List<Account> searchAccountsByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Account> query = session.createQuery("from Account a where a.name like :name", Account.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }

    public static List<Account> searchAccountsByBank(Session session, String bank) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(bank);

        Query<Account> query = session.createQuery("from Account a where a.bank like :bank", Account.class);
        query.setParameter("bank", "%" + bank + "%");

        return query.list();
    }
}
