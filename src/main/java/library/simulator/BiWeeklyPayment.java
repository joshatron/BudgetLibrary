package library.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BiWeeklyPayment extends Payment {

    private DayOfWeek dayOfWeek;
    private boolean payNext;

    public BiWeeklyPayment(int amount, String name, DayOfWeek dayOfWeek, boolean payNext) {
        super(amount, name);
        this.dayOfWeek = dayOfWeek;
        this.payNext = payNext;
    }

    @Override
    public boolean executesToday(LocalDate date) {
        if(date.getDayOfWeek() == dayOfWeek) {
            if(payNext) {
                payNext = false;
                return true;
            }
            else {
                payNext = true;
                return false;
            }
        }
        return false;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
