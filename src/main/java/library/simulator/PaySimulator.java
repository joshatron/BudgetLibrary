package library.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaySimulator {

    public static void main(String[] args) {
        int startingAmount = 1550;
        int weekPay = 650;
        DayOfWeek payDay = DayOfWeek.TUESDAY;
        int years = 2;
        Expense rent = new Expense(-1, 1000, "rent");
        Expense creditCard = new Expense(22, 1600, "credit card");
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(rent);
        expenses.add(creditCard);

        int currentAmount = startingAmount;
        LocalDate date = LocalDate.now();
        int min = startingAmount;
        LocalDate minDate = date;
        int max = startingAmount;
        LocalDate maxDate = date;

        for(int i = 0; i < years * 365; i++) {
            if(date.getDayOfWeek() == payDay) {
                currentAmount += weekPay;
            }

            for(Expense expense : expenses) {
                if (expense.executesToday(date)) {
                    currentAmount -= expense.getAmount();
                }
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
