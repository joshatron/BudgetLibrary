package io.joshatron.budgetlibrary.objects;

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
    private Money amount;
    private Vendor vendor;

    public Transaction() {
        this.timestamp = null;
        this.amount = new Money(0);
        this.vendor = null;
    }

    public Transaction(Timestamp timestamp, Money amount, Vendor vendor) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.vendor = vendor;
    }

    public boolean isValid() {
        if (timestamp == null || timestamp.getTimestampLong() == -1) {
            return false;
        }
        if (vendor == null || !vendor.isValid()) {
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

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String toString() {
        return "Date: " + timestamp.getTimestampString() + " Amount: " + amount.toString() + " Vendor: " + vendor.getName() + " Type: " + vendor.getType();
    }
}

