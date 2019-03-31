package io.joshatron.budgetlibrary.dtos;

/*
 * Data held by a transaction
 *
 * Fields include:
 * id: id of the transaction, -1 if there is none
 * timestamp: time when transaction took place
 * amount: total amount of transaction, negative amount means money was received
 * vendor: vendor for transaction
 * account: which account the transaction is on
 */
public class Transaction {
    private int id;
    private Timestamp timestamp;
    private Money amount;
    private Vendor vendor;
    private Account account;

    public Transaction() {
        this.id = -1;
        this.timestamp = null;
        this.amount = new Money(0);
        this.vendor = null;
        this.account = null;
    }

    public Transaction(Timestamp timestamp, Money amount, Vendor vendor, Account account) {
        this.id = -1;
        this.timestamp = timestamp;
        this.amount = amount;
        this.vendor = vendor;
        this.account = account;
    }

    public Transaction(int id, Timestamp timestamp, Money amount, Vendor vendor, Account account) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.vendor = vendor;
        this.account = account;
    }

    public boolean isValid() {
        return id != -1 && timestamp != null && amount != null && vendor != null && vendor.isValid() &&
               account != null && account.isValid();
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        return "Date: " + timestamp.getTimestampString() + " Amount: " + amount.toString() + " Vendor: " + vendor.getName() + " Type: " + vendor.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

