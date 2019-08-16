package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.analysis.TimeframeAnalysis;
import io.joshatron.budgetlibrary.dtos.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Class to handle all printing of data from the database
 */
public class PrintHandler {

    public static void printTransactions(List<Transaction> transactions) {
        System.out.println("Timestamp      Amount         Account        Vendor                        Type");
        System.out.println("----------------------------------------------------------------------------------------------");
        for(Transaction transaction : transactions) {
            System.out.println(String.format("%-15s%-15s%-15s%-30s%s", transaction.getTimestamp().getTimestampString(),
                    transaction.getAmount().toString(), transaction.getAccount().getName(), transaction.getVendor().getName(), transaction.getVendor().getType().getName()));
        }
    }

    public static void printVendors(List<Vendor> vendors) {
        System.out.println("Vendor                       Type");
        System.out.println("-------------------------------------------------");

        for(Vendor vendor : vendors) {
            System.out.println(String.format("%-30s%s", vendor.getName(), vendor.getType().getName()));
        }
    }

    public static void printTimeframeAnalysis(TimeframeAnalysis analysis) {
        System.out.println("Analysis for transactions from " + analysis.getTransactions().get(0).getTimestamp().toString() +
                " to " + analysis.getTransactions().get(analysis.getTransactions().size() - 1).getTimestamp().toString());

        System.out.println("Total money in: " + analysis.getTotalIn().toString());
        System.out.println("Total money out: " + analysis.getTotalOut().toString());

        System.out.println("Spending per vendor:");
        System.out.println("-------------------------------------------------");
        for(Map.Entry<Vendor, Money> entry : analysis.getSpendingPerVendor().entrySet().stream()
                .sorted((e1,e2) -> e1.getKey().getName().compareTo(e2.getKey().getName())).collect(Collectors.toList())) {
            System.out.println(String.format("%-30s%s", entry.getKey().getName(), entry.getValue().toString()));
        }

        System.out.println("Spending per type:");
        System.out.println("-------------------------------------------------");
        for(Map.Entry<Type, Money> entry : analysis.getSpendingPerType().entrySet().stream()
                .sorted((e1,e2) -> e1.getKey().getName().compareTo(e2.getKey().getName())).collect(Collectors.toList())) {
            System.out.println(String.format("%-30s%s", entry.getKey().getName(), entry.getValue().toString()));
        }

        System.out.println("Spending per day:");
        System.out.println("-------------------------------------------------");
        for(Map.Entry<Timestamp, Money> entry : analysis.getSpendingPerDay().entrySet().stream()
                .sorted((e1,e2) -> e1.getKey().toString().compareTo(e2.getKey().toString())).collect(Collectors.toList())) {
            System.out.println(String.format("%-30s%s", entry.getKey().toString(), entry.getValue().toString()));
        }
    }
}
