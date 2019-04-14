package io.joshatron.budgetlibrary.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Money {
    @Column
    private int amount;

    //constructor in cents
    public Money(int amount) {
        this.amount = amount;
    }

    //constructor in dollars
    public Money(double amount) {
        this.amount = Math.toIntExact(Math.round(amount * 100));
    }

    //constructor for a string representation.
    //it will remove all $ and , from the input automatically.
    public Money(String amount) {
        amount = amount.replace("$", "");
        amount = amount.replace(",", "");
        this.amount = Math.toIntExact(Math.round(Double.parseDouble(amount) * 100));
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

    public void reverseSign() {
        amount = amount * -1;
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
