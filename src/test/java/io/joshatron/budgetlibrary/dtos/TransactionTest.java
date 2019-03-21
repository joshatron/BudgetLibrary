package io.joshatron.budgetlibrary.dtos;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionTest {

    private Transaction createTransaction() {
        Vendor vendor = new Vendor("Safeway", "grocery");

        Transaction transaction = new Transaction(new Timestamp("2017-09-01"), new Money(10000), vendor, "citi");

        return transaction;
    }

    @Test
    public void testIsValidTrue() throws Exception {
        Transaction transaction = createTransaction();
        Assert.assertEquals(transaction.isValid(), true);
    }

    @Test
    public void testIsValidNullTimestamp() throws Exception {
        Transaction transaction = createTransaction();
        transaction.setTimestamp(null);
        Assert.assertEquals(transaction.isValid(), false);
    }

    @Test
    public void testIsValidInvalidTimestamp() throws Exception {
        Transaction transaction = createTransaction();
        transaction.setTimestamp(new Timestamp(-1));
        Assert.assertEquals(transaction.isValid(), false);
    }

    @Test
    public void testIsValidNullVendor() throws Exception {
        Transaction transaction = createTransaction();
        transaction.setVendor(null);
        Assert.assertEquals(transaction.isValid(), false);
    }

    @Test
    public void testIsValidInvalidVendor() throws Exception {
        Transaction transaction = createTransaction();
        transaction.setVendor(new Vendor());
        Assert.assertEquals(transaction.isValid(), false);
    }

    @Test
    public void testSettersGetters() throws Exception {
        Timestamp timestamp = new Timestamp("2017-09-01");
        int amount = 10000;
        Vendor vendor = new Vendor("Safeway", null);
        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        transaction.setAmount(new Money(amount));
        transaction.setVendor(vendor);
        Assert.assertEquals(transaction.getTimestamp().getTimestampString(), timestamp.getTimestampString());
        Assert.assertEquals(transaction.getAmount().getAmountInCents(), amount);
        Assert.assertEquals(transaction.getVendor().getName(), vendor.getName());
    }
}
