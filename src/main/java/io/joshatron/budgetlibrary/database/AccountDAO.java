package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;

import java.util.List;

public class AccountDAO {

    public static Account createAccount(Session session, String name, String bank, String description) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = new Account();
        account.setName(name);
        account.setBank(bank);
        account.setDescription(description);
        session.save(account);

        tx.commit();

        return account;
    }

    public static void updateAccount(Session session, int accountId, String name, String bank, String description) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
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

    public static void deleteAccount(Session session, int accountId) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        session.delete(account);

        tx.commit();

    }

    public static List<Account> getAccounts(Session session, String name, String bank, String description) throws BudgetLibraryException {
        return null;
    }
}
