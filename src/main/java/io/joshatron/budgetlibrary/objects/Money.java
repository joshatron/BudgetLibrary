package io.joshatron.budgetlibrary.objects;

public class Money {
    private int amount;

    //constructor in cents
    public Money(int amount) {
        this.amount = amount;
    }

    //constructor in dollars
    public Money(double amount) {
        this.amount = Math.toIntExact(Math.round(amount * 100));
    }

    public int getAmountInCents() {
        return amount;
    }

    public double getAmountInDollars() {
        return amount / 100.;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAmount(double amount) {
        this.amount = Math.toIntExact(Math.round(amount * 100));
    }

    public String toString() {
        if(amount >= 0) {
            return String.format("$%.2f", amount / 100.);
        }
        else {
            return String.format("-$%.2f", amount / -100.);
        }
    }
}
