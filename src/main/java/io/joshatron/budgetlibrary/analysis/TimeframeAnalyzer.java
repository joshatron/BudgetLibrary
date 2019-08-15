package io.joshatron.budgetlibrary.analysis;

import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Transaction;

import java.util.List;

public class TimeframeAnalyzer {

    private static TimeframeAnalysis gatherStatistics(List<Transaction> transactions) {
        transactions.sort((t1, t2) -> (int)(t1.getTimestamp().getTimestampLong() - t2.getTimestamp().getTimestampLong()));
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

        System.out.println("Stats for range " + transactions.get(0).getTimestamp().toString() + " - " +
                transactions.get(transactions.size() - 1).getTimestamp().toString());
        System.out.println("Total money in: " + in.toString());
        System.out.println("Total money out: " + out.toString());

        return null;
    }
}
