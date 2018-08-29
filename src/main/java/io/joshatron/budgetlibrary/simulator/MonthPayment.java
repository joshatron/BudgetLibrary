package io.joshatron.budgetlibrary.simulator;

import java.time.LocalDate;

public class MonthPayment extends Payment {

    //0 or negative means from end
    private int dayOfMonth;

    public MonthPayment(int amount, String name, int dayOfMonth) {
        super(amount, name);
        this.dayOfMonth = dayOfMonth;
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
}
