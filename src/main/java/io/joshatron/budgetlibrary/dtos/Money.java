package io.joshatron.budgetlibrary.dtos;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@Embeddable
public class Money {
    @Column(nullable = false)
    private int amount;

    public Money() {
        amount = 0;
    }

    //constructor in cents
    public Money(int amount) {
        this.amount = amount;
    }

    public Money(Money money) {
        this.amount = money.getAmountInCents();
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

    public void add(Money money) {
        amount += money.getAmountInCents();
    }

    public void subtract(Money money) {
        amount -= money.getAmountInCents();
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
