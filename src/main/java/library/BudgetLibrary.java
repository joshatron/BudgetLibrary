package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BudgetLibrary {

    private Connection c;

    public BudgetLibrary() {
        initalizeDatabase();
    }

    private boolean initalizeDatabase() {
        String database = "budget.db";

        String transactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id integer PRIMARY KEY," +
                "timestamp text NOT NULL," +
                "amount real NOT NULL," +
                "vendor integer NOT NULL);";

        String vendorsTable = "CREATE TABLE IF NOT EXISTS vendors (" +
                "id integer PRIMARY KEY," +
                "name text NOT NULL," +
                "nickname text," +
                "category integer NOT NULL);";

        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "id integer PRIMARY KEY," +
                "category text NOT NULL," +
                "description text NOT NULL," +
                "budget real NOT NULL);";

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + database);
            Statement statements = c.createStatement();
            statements.executeUpdate(transactionsTable);
            statements.executeUpdate(vendorsTable);
            statements.executeUpdate(categoriesTable);
            statements.close();

            System.out.println("Successfully initialized database");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addTransaction(String timestamp, double amount, String vendor) {
        return false;
    }

    public boolean addVendor(String name, String nickname, String category) {
        return false;
    }

    public boolean addCategory(String name, String desctiption, double budget) {
        return false;
    }

    public boolean vendorExists(String name) {
        return false;
    }

    public boolean categoryExists(String name) {
        return false;
    }
}
