package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Timestamp;
import io.joshatron.budgetlibrary.dtos.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class MonthBreakdown {

    public static void printMonthBreakdown(List<Transaction> transactions) {
        Timestamp oldest = transactions.get(0).getTimestamp();
        for(Transaction transaction : transactions) {
            if(transaction.getTimestamp().getTimestampLong() < oldest.getTimestampLong()) {
                oldest = transaction.getTimestamp();
            }
        }

        int year = oldest.getYear();
        int month = oldest.getMonth();
        List<Transaction> currentMonth = getTransactionsInMonth(year, month, transactions);

        while(!currentMonth.isEmpty()) {
            printMonthStatistics(currentMonth);
            month = (month + 1) % 13;
            if(month == 0) {
                year++;
                month++;
            }
            currentMonth = getTransactionsInMonth(year, month, transactions);
        }
    }

    private static List<Transaction> getTransactionsInMonth(int year, int month, List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> transaction.getTimestamp().getYear() == year && transaction.getTimestamp().getMonth() == month).collect(Collectors.toList());
    }

    private static void printMonthStatistics(List<Transaction> transactions) {
        Money in = new Money(0);
        Money out = new Money(0);

        for(Transaction transaction : transactions) {
            if(transaction.getAmount().getAmountInCents() > 0) {
                in.setAmount(transaction.getAmount().getAmountInCents() + in.getAmountInCents());
            }
            else {
                out.setAmount(out.getAmountInCents() - transaction.getAmount().getAmountInCents());
            }
        }

        System.out.println("Stats for " + transactions.get(0).getTimestamp().getMonth() + "/" + transactions.get(0).getTimestamp().getYear());
        System.out.println("Total money in: " + in.toString());
        System.out.println("Total money out: " + out.toString());
    }
}
