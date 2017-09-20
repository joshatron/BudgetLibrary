package library.operations;

import library.objects.Category;
import library.objects.Transaction;
import library.objects.Vendor;

import java.util.ArrayList;

/*
 * Class to handle all printing of data from the database
 */
public class PrintHandler {

    public static void printTransactions(ArrayList<Transaction> transactions) {
        System.out.println("Timestamp      Amount          Vendor");
        for(Transaction transaction : transactions) {
            System.out.println(String.format("%-15s$%-15.2f%s", transaction.getTimestamp().getTimestampString(),
                    (transaction.getAmount() / 100.), transaction.getVendor().getName()));
        }
    }

    public static void printVendors(ArrayList<Vendor> vendors) {
        System.out.println("Vendor              Category");
        for(Vendor vendor : vendors) {
            System.out.println(String.format("%-20s%s", vendor.getName(), vendor.getCategory().getName()));
        }
    }

    public static void printCategories(ArrayList<Category> categories) {
        System.out.println("Category            Budget          Description");
        for(Category category : categories) {
            System.out.println(String.format("%-20s$%-15.2f%s", category.getName(), (category.getBudget() / 100.), category.getDescription()));
        }
    }
}
