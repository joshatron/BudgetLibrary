package library;

import java.util.Date;

/*
 * Data held by transaction
 *
 * Fields include:
 * timestamp: time when transaction took place
 * amount: total amount of transaction
 * vendor: vendor for transaction
 */
public class Transaction {
    private Date timestamp;
    private double amount;
    private Vendor vendor;

    public Transaction() {
        this.timestamp = null;
        this.amount = 0;
        this.vendor = null;
    }

    public Transaction(Date timestamp, double amount, Vendor vendor) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.vendor = vendor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
