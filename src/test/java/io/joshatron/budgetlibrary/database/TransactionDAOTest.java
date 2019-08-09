package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TransactionDAOTest {
    Session session;

    @BeforeTest
    public void initialize() {
        session = DAOUtils.createSession();
    }

    @AfterMethod
    public void deleteAfter() {
        DAOUtils.cleanUp(session);
    }

    @AfterTest
    public void closeSession() {
        session.close();
    }

    @Test
    public void createTransactionBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Transaction transaction = TransactionDAO.createTransaction(session, new Timestamp("2019-08-01"), new Money(34.62), vendor, account);
            Assert.assertEquals(transaction.getTimestamp(), new Timestamp("2019-08-01"));
            Assert.assertEquals(transaction.getAmount(), new Money(34.62));
            Assert.assertEquals(transaction.getVendor(), vendor);
            Assert.assertEquals(transaction.getAccount(), account);

            List<Transaction> transactions = TransactionDAO.getAllTransactions(session);
            Assert.assertEquals(transactions.size(), 1);
            Assert.assertEquals(transactions.get(0), transaction);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
