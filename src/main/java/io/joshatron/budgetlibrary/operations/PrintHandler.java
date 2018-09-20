package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.objects.Transaction;
import io.joshatron.budgetlibrary.objects.Vendor;

import java.util.ArrayList;

/*
 * Class to handle all printing of data from the database
 */
public class PrintHandler {

    public static void printTransactions(ArrayList<Transaction> transactions) {
        System.out.println("Timestamp      Amount          Vendor");
        for(Transaction transaction : transactions) {
            System.out.println(String.format("%-15s$%-15s%s", transaction.getTimestamp().getTimestampString(),
                    transaction.getAmount().toString(), transaction.getVendor().getName()));
        }
    }

    public static void printVendors(ArrayList<Vendor> vendors) {
        System.out.println("Vendor");
        for(Vendor vendor : vendors) {
            System.out.println(vendor.getName());
        }
    }
}
