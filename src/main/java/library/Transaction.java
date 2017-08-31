package library;

import java.util.Date;

/*
 * Data held by transaction
 *
 * Fields include:
 * timestamp: time when transaction took place
 * amount: total amount of transaction, negative amount means money was received
 * vendor: vendor for transaction
 */
public class Transaction {
    private Timestamp timestamp;
    private double amount;
    private Vendor vendor;

    public Transaction() {
        this.timestamp = null;
        this.amount = 0;
        this.vendor = null;
    }

    public Transaction(Timestamp timestamp, double amount, Vendor vendor) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.vendor = vendor;
    }

    public boolean isValid() {
        if(timestamp == null || timestamp.getTimestamp() == -1) {
            System.out.println("Transaction timestamp null");
            return false;
        }
        if(vendor == null || !vendor.isValid()) {
            System.out.println("Vendor can't be invalid");
            return false;
        }
        return true;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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
