package library.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PaySimulator {

    public static void main(String[] args) {
        int startingAmount = 1550;
        int weekPay = 650;
        DayOfWeek payDay = DayOfWeek.TUESDAY;
        int expense1Day = 22;
        int expense1Amount = 1600;
        int expense2Day = -1;
        int expense2Amount = 1000;

        int currentAmount = startingAmount;
        LocalDate date = LocalDate.now();
        int min = startingAmount;
        LocalDate minDate = date;
        int max = startingAmount;
        LocalDate maxDate = date;

        for(int i = 0; i < 700; i++) {
            if(date.getDayOfWeek() == payDay) {
                currentAmount += weekPay;
            }
            if(date.getDayOfMonth() == expense1Day) {
                currentAmount -= expense1Amount;
            }
            if(date.lengthOfMonth() - date.getDayOfMonth() == expense2Day * -1) {
                currentAmount -= expense2Amount;
            }

            if(currentAmount < min) {
                min = currentAmount;
                minDate = date;
            }
            if(currentAmount > max) {
                max = currentAmount;
                maxDate = date;
            }

            if(date.getDayOfMonth() == 1) {
                System.out.println("Day: " + date.toString() + ", Money: $" + currentAmount);
            }
            if(currentAmount < 0) {
                System.out.println("You ran out of money on " + date.toString());
                break;
            }

            date = date.plusDays(1);
        }

        System.out.println();
        System.out.println("Min: $" + min + " on " + minDate.toString());
        System.out.println("Max: $" + max + " on " + maxDate.toString());
    }
}
