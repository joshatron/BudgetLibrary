package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.dtos.Transaction;
import io.joshatron.budgetlibrary.dtos.Vendor;

import java.util.ArrayList;

/*
 * Class to handle all printing of data from the database
 */
public class PrintHandler {

    public static void printTransactions(ArrayList<Transaction> transactions) {
        System.out.println("Timestamp      Amount         Account        Vendor                        Type");
        System.out.println("----------------------------------------------------------------------------------------------");
        for(Transaction transaction : transactions) {
            System.out.println(String.format("%-15s%-15s%-15s%-30s%s", transaction.getTimestamp().getTimestampString(),
                    transaction.getAmount().toString(), transaction.getAccount(), transaction.getVendor().getName(), transaction.getVendor().getType()));
        }
    }

    public static void printVendors(ArrayList<Vendor> vendors) {
        System.out.println("Vendor                       Type");
        System.out.println("-------------------------------------------------");

        for(Vendor vendor : vendors) {
            System.out.println(String.format("%-30s%s", vendor.getName(), vendor.getType()));
        }
    }
}
