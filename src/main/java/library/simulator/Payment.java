package library.simulator;

import java.time.LocalDate;

public abstract class Payment {

    private int amount;
    private String name;

    public Payment(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public abstract boolean executesToday(LocalDate date);

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
