package library;

import library.objects.Timestamp;
import library.objects.Transaction;
import library.objects.Vendor;
import library.operations.BudgetLibrary;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        BudgetLibrary library = new BudgetLibrary();

        ArrayList<String> tags = new ArrayList<String>();
        tags.add("rent");
        tags.add("subscriptions");
        ArrayList<String> nicknames = new ArrayList<String>();
        nicknames.add("RIVERVIEW APARTMENTS");
        Vendor vendor = new Vendor("River view", nicknames, tags);

        Transaction transaction1 = new Transaction(new Timestamp("2017-08-01 10:00:00"), 2000, vendor);
        Transaction transaction2 = new Transaction(new Timestamp("2017-09-01 10:00:00"), 2000, vendor);

        library.addTransaction(transaction1);
        library.addTransaction(transaction2);
    }
}
