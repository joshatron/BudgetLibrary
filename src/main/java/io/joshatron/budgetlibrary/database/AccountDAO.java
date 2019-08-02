package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

    public static void updateAccount(Session session, long accountId, String name, String bank, String description) throws BudgetLibraryException {
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

    public static void deleteAccount(Session session, long accountId) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Account account = session.get(Account.class, accountId);
        session.delete(account);

        tx.commit();

    }

    public static List<Account> getAccounts(Session session, String name, String bank, String description) throws BudgetLibraryException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("from Account a");
        if(isValid(name) || isValid(bank) || isValid(description)) {
            queryString.append(" where");

            boolean first = true;
            if(isValid(name)) {
                first = false;
                queryString.append(" a.name=:name");
            }
            if(isValid(bank)) {
                if(!first) {
                    queryString.append(" and");
                }
                first = false;
                queryString.append(" a.bank=:bank");
            }
            if(isValid(description)) {
                if(!first) {
                    queryString.append(" and");
                }
                queryString.append(" a.description=:description");
            }
        }

        Query<Account> query = session.createQuery(queryString.toString(), Account.class);
        if(isValid(name)) {
            query.setParameter("name", name);
        }
        if(isValid(bank)) {
            query.setParameter("bank", bank);
        }
        if(isValid(description)) {
            query.setParameter("description", description);
        }

        return query.list();
    }

    private static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }
}
