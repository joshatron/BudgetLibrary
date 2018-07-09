package library.objects;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TransactionTest {

    private Transaction createTransaction() {
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor("Safeway", tags);

        Transaction transaction = new Transaction(new Timestamp("2017-09-01"), 10000, vendor);

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
        transaction.setAmount(amount);
        transaction.setVendor(vendor);
        Assert.assertEquals(transaction.getTimestamp().getTimestampString(), timestamp.getTimestampString());
        Assert.assertEquals(transaction.getAmount(), amount);
        Assert.assertEquals(transaction.getVendor().getName(), vendor.getName());
    }
}