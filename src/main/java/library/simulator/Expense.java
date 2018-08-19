package library.simulator;

import java.time.LocalDate;

public class Expense {

    //0 or negative means from end
    private int dayOfMonth;
    private int amount;
    private String name;

    public Expense(int dayOfMonth, int amount, String name) {
        this.dayOfMonth = dayOfMonth;
        this.amount = amount;
        this.name = name;
    }

    public boolean executesToday(LocalDate date) {
        //from beginning
        if(dayOfMonth > 0) {
            return date.getDayOfMonth() == dayOfMonth;
        }
        //from end
        else {
            return date.lengthOfMonth() - date.getDayOfMonth() == dayOfMonth * -1;
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

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
