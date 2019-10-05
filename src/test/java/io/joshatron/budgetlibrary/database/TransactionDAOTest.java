package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
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
            Transaction transaction = TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            Assert.assertEquals(transaction.getTimestamp(), LocalDate.of(2019,8,1));
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

    @Test
    public void updateTransactionBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking_2", "bank_2", "main checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Transaction transaction = TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.updateTransaction(session, transaction.getId(), LocalDate.of(2019,9,1), new Money(26.75), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.getAllTransactions(session);
            Assert.assertEquals(transactions.size(), 1);
            Assert.assertEquals(transactions.get(0).getTimestamp(), LocalDate.of(2019,9,1));
            Assert.assertEquals(transactions.get(0).getAmount(), new Money(26.75));
            Assert.assertEquals(transactions.get(0).getVendor(), vendor2);
            Assert.assertEquals(transactions.get(0).getAccount(), account2);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void deleteTransactionBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Transaction transaction = TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.deleteTransaction(session, transaction.getId());

            List<Transaction> transactions = TransactionDAO.getAllTransactions(session);
            Assert.assertEquals(transactions.size(), 0);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getAllTransactionsBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.getAllTransactions(session);
            Assert.assertEquals(transactions.size(), 5);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getTransactionsByVendorBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.getTransactionsByVendor(session, vendor);
            Assert.assertEquals(transactions.size(), 2);
            transactions = TransactionDAO.getTransactionsByVendor(session, vendor2);
            Assert.assertEquals(transactions.size(), 2);
            transactions = TransactionDAO.getTransactionsByVendor(session, vendor3);
            Assert.assertEquals(transactions.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getTransactionsByTypeBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.getTransactionsByType(session, type);
            Assert.assertEquals(transactions.size(), 4);
            transactions = TransactionDAO.getTransactionsByType(session, type2);
            Assert.assertEquals(transactions.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getTransactionsByAccountBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.getTransactionsByAccount(session, account);
            Assert.assertEquals(transactions.size(), 2);
            transactions = TransactionDAO.getTransactionsByAccount(session, account2);
            Assert.assertEquals(transactions.size(), 3);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void searchTransactionsBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            Transaction transaction = TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.searchTransactions(session, LocalDate.of(2019,8,2), LocalDate.of(2019,8,3),
                    new Money(40d), new Money(70d), vendor3, account2, null);
            Assert.assertEquals(transactions.size(), 1);
            Assert.assertEquals(transactions.get(0), transaction);
            transactions = TransactionDAO.searchTransactions(session, LocalDate.of(2019,8,2), LocalDate.of(2019,8,3),
                    new Money(40d), new Money(70d), null, account2, type2);
            Assert.assertEquals(transactions.size(), 1);
            Assert.assertEquals(transactions.get(0), transaction);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void searchTransactionsByTimeRangeBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.searchTransactionsByTimeRange(session, LocalDate.of(2019,8,2), LocalDate.of(2019,8,4));
            Assert.assertEquals(transactions.size(), 3);
            transactions = TransactionDAO.searchTransactionsByTimeRange(session, LocalDate.of(2019,7,1), LocalDate.of(2019,9,1));
            Assert.assertEquals(transactions.size(), 5);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void searchTransactionsByMoneyRangeBasic() {
        try {
            Account account = AccountDAO.createAccount(session, "checking", "bank", "main checking");
            Account account2 = AccountDAO.createAccount(session, "checking2", "bank2", "secondary checking");
            Type type = TypeDAO.createType(session, "groceries", "food from grocery stores");
            Type type2 = TypeDAO.createType(session, "gas", "gas for car");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Vendor vendor2 = VendorDAO.createVendor(session, "trader joe's", type);
            Vendor vendor3 = VendorDAO.createVendor(session, "shell", type2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,1), new Money(34.62), vendor, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,2), new Money(44.62), vendor2, account);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,3), new Money(54.62), vendor3, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,4), new Money(64.62), vendor, account2);
            TransactionDAO.createTransaction(session, LocalDate.of(2019,8,5), new Money(74.62), vendor2, account2);

            List<Transaction> transactions = TransactionDAO.searchTransactionsByMoneyRange(session, new Money(20d), new Money(50d));
            Assert.assertEquals(transactions.size(), 2);
            transactions = TransactionDAO.searchTransactionsByMoneyRange(session, new Money(44.62), new Money(70d));
            Assert.assertEquals(transactions.size(), 3);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
