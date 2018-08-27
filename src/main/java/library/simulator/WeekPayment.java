package library.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekPayment extends Payment {

    private DayOfWeek dayOfWeek;

    public WeekPayment(int amount, String name, DayOfWeek dayOfWeek) {
        super(amount, name);
        this.dayOfWeek = dayOfWeek;
    }

    public boolean executesToday(LocalDate date) {
        if(date.getDayOfWeek() == dayOfWeek) {
            return true;
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
