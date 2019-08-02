package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AccountDAOTest {
    Session session;

    @BeforeMethod
    public void initialize() {
        session = DAOUtils.createSession();
    }

    @AfterMethod
    public void deleteAfter() {
        DAOUtils.cleanUp(session);
    }

    @Test
    public void createAccountBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            Assert.assertEquals(account.getName(), "checking");
            Assert.assertEquals(account.getBank(), "bank");
            Assert.assertEquals(account.getDescription(), "checking for bank");

            List<Account> accounts = AccountDAO.getAccounts(session, null, null, null);
            Assert.assertEquals(accounts.size(), 1);
            Assert.assertEquals(accounts.get(0), account);
        } catch(BudgetLibraryException e) {
        }
    }

    @Test
    public void updateAccountBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            AccountDAO.updateAccount(session, account.getId(), "checking_2", "bank_2", "checking_2 for bank_2");

            List<Account> accounts = AccountDAO.getAccounts(session, null, null, null);
            Assert.assertEquals(accounts.size(), 1);
            Assert.assertEquals(accounts.get(0).getName(), "checking_2");
            Assert.assertEquals(accounts.get(0).getBank(), "bank_2");
            Assert.assertEquals(accounts.get(0).getDescription(), "checking_2 for bank_2");
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void deleteAccountBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            AccountDAO.deleteAccount(session, account.getId());

            List<Account> accounts = AccountDAO.getAccounts(session, null, null, null);
            Assert.assertEquals(accounts.size(), 0);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getAccountsByName() {
        try {
            AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            AccountDAO.createAccount(session, "checking_2", "bank_2", "checking for bank_2");
            AccountDAO.createAccount(session, "checking_3", "bank_2", "checking_2 for bank_2");

            List<Account> accounts = AccountDAO.getAccounts(session, "checking", null, null);
            Assert.assertEquals(accounts.size(), 1);
            accounts = AccountDAO.getAccounts(session, "checking_2", null, null);
            Assert.assertEquals(accounts.size(), 1);
            accounts = AccountDAO.getAccounts(session, "checking_3", null, null);
            Assert.assertEquals(accounts.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getAccountsByBank() {
        try {
            AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            AccountDAO.createAccount(session, "checking_2", "bank", "checking_2 for bank");
            AccountDAO.createAccount(session, "checking_3", "bank_2", "checking_2 for bank_2");
            AccountDAO.createAccount(session, "checking_4", "bank_2", "checking for bank");

            List<Account> accounts = AccountDAO.getAccounts(session, null, "bank", null);
            Assert.assertEquals(accounts.size(), 2);
            accounts = AccountDAO.getAccounts(session, null, "bank_2", null);
            Assert.assertEquals(accounts.size(), 2);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getAccountsByDescription() {
        try {
            AccountDAO.createAccount(session, "checking", "bank", "checking for bank");
            AccountDAO.createAccount(session, "checking_2", "bank_2", "checking for bank");
            AccountDAO.createAccount(session, "checking_3", "bank_2", "checking_2 for bank_2");
            AccountDAO.createAccount(session, "checking_4", "bank_3", "checking_2 for bank_3");

            List<Account> accounts = AccountDAO.getAccounts(session, null, null, "checking for bank");
            Assert.assertEquals(accounts.size(), 2);
            accounts = AccountDAO.getAccounts(session, null, null, "checking_2 for bank_2");
            Assert.assertEquals(accounts.size(), 1);
            accounts = AccountDAO.getAccounts(session, null, null, "checking_2 for bank_3");
            Assert.assertEquals(accounts.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
