package library;

public class App {
    public static void main(String[] args) {
        BudgetLibrary library = new BudgetLibrary();

        Category category = new Category("Rent", "rent", 2000);
        Vendor vendor = new Vendor("River view", null, null, category);
        Transaction transaction1 = new Transaction(new Timestamp("2017-08-01 10:00:00"), 2000, vendor);
        Transaction transaction2 = new Transaction(new Timestamp("2017-09-01 10:00:00"), 2000, vendor);

        library.addTransaction(transaction1);
        library.addTransaction(transaction2);

        library.printTransactions();
        System.out.println();
        library.printVendors();
        System.out.println();
        library.printCategories();
    }

}
