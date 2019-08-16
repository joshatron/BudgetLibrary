package io.joshatron.budgetlibrary.analysis;

import io.joshatron.budgetlibrary.dtos.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeframeAnalyzer {

    public static TimeframeAnalysis gatherStatistics(List<Transaction> transactions) {
        transactions.sort((t1, t2) -> (int)(t1.getTimestamp().getTimestampLong() - t2.getTimestamp().getTimestampLong()));
        Money in = new Money(0);
        Money out = new Money(0);
        Map<Vendor,Money> moneyPerVendor = new HashMap<>();
        Map<Type,Money> moneyPerType = new HashMap<>();
        Map<Timestamp,Money> moneyPerDay = new HashMap<>();

        for(Transaction transaction : transactions) {
            if(transaction.getAmount().getAmountInCents() > 0) {
                in.add(transaction.getAmount());
            }
            else {
                out.subtract(transaction.getAmount());
            }

            if(moneyPerVendor.containsKey(transaction.getVendor())) {
                moneyPerVendor.get(transaction.getVendor()).add(transaction.getAmount());
            }
            else {
                moneyPerVendor.put(transaction.getVendor(), new Money(transaction.getAmount()));
            }

            if(moneyPerType.containsKey(transaction.getVendor().getType())) {
                moneyPerType.get(transaction.getVendor().getType()).add(transaction.getAmount());
            }
            else {
                moneyPerType.put(transaction.getVendor().getType(), new Money(transaction.getAmount()));
            }

            if(moneyPerDay.containsKey(transaction.getTimestamp())) {
                moneyPerDay.get(transaction.getTimestamp()).add(transaction.getAmount());
            }
            else {
                moneyPerDay.put(transaction.getTimestamp(), new Money(transaction.getAmount()));
            }
        }

        return new TimeframeAnalysis(transactions, in, out, moneyPerVendor, moneyPerType, moneyPerDay);
    }
}
