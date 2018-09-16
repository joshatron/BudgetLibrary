package io.joshatron.budgetlibrary.simulator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaySimulator {

    public static void main(String[] args) {
        int startingAmount = 350000;
        int years = 2;
        MonthPayment rent = new MonthPayment(-100000, "rent", -1);
        MonthPayment creditCard = new MonthPayment(-250000, "credit card", 22);
        BiWeeklyPayment income = new BiWeeklyPayment(230000, "income", DayOfWeek.FRIDAY, false);
        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(rent);
        payments.add(creditCard);
        payments.add(income);

        runSimulation(startingAmount, years, payments);
    }

    public static int runSimulation(int startingAmount, int years, ArrayList<Payment> payments) {
        int currentAmount = startingAmount;
        int debt = 0;
        LocalDate date = LocalDate.now();
        int min = startingAmount;
        LocalDate minDate = date;
        int max = startingAmount;
        LocalDate maxDate = date;

        for(int i = 0; i < years * 365; i++) {

            for(Payment payment : payments) {
                if (payment.executesToday(date)) {
                    currentAmount += payment.getAmount();
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
                System.out.println("You ran out of money on " + date.toString() + ". Resolving debt and continuing.");
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
