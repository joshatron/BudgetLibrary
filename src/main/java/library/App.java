package library;

public class App {
    public static void main(String[] args) {
        BudgetLibrary library = new BudgetLibrary();

        library.addCategory("subscriptions", "rent, utitlites, online subscriptions", 2000);
        library.addVendor("river view", "rent", "subscriptions");
        library.addTransaction("07/01/17 09:00:00", 2000, "river view");
        library.addTransaction("08/01/17 09:00:00", 2000, "river view");
        library.printTransactions();
        library.printVendors();
        library.printCategories();
    }

}
