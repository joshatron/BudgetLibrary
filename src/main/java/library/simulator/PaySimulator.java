package library.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaySimulator {

    public static void main(String[] args) {
        int startingAmount = 51600;
        int weekPay = 71000;
        DayOfWeek payDay = DayOfWeek.TUESDAY;
        int years = 2;
        Expense rent = new Expense(-1, 100000, "rent");
        Expense creditCard = new Expense(22, 180000, "credit card");
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(rent);
        expenses.add(creditCard);

        runSimulation(startingAmount, weekPay, payDay, years, expenses);
    }

    public static int runSimulation(int startingAmount, int weekPay, DayOfWeek payDay, int years, ArrayList<Expense> expenses) {
        int currentAmount = startingAmount;
        int debt = 0;
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
                System.out.println("Day: " + date.toString() + ", Money: $" + (currentAmount / 100.));
            }
            if(currentAmount < 0) {
                System.out.println("You ran out of money on " + date.toString());
                debt -= currentAmount;
                currentAmount = 0;
            }

            date = date.plusDays(1);
        }

        System.out.println();
        System.out.println("Min: $" + (min / 100.) + " on " + minDate.toString());
        System.out.println("Max: $" + (max / 100.) + " on " + maxDate.toString());
        System.out.println("End: $" + (currentAmount / 100.));
        System.out.println("Debt: $" + (debt / 100.));

        return currentAmount;
    }
}
