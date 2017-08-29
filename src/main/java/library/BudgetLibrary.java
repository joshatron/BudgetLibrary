package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BudgetLibrary {

    private Connection conn;

    public BudgetLibrary() {
        initalizeDatabase();
    }

    private boolean initalizeDatabase() {
        String database = "budget.db";

        String transactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "timestamp text NOT NULL," +
                "amount real NOT NULL," +
                "vendor integer NOT NULL);";

        String vendorsTable = "CREATE TABLE IF NOT EXISTS vendors (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "name text NOT NULL," +
                "category integer NOT NULL);";

        String vendorTagsTable = "CREATE TABLE IF NOT EXISTS vendor_tags (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "name text NOT NULL," +
                "description text);";

        String vendorTaggingsTable = "CREATE TABLE IF NOT EXISTS vendor_taggings (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "vendor_id integer NOT NULL," +
                "tag_id integer NOT NULL);";

        String vendorNamingsTable = "CREATE TABLE IF NOT EXISTS vendor_namings (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "vendor_id integer NOT NULL," +
                "name text NOT NULL);";

        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "category text NOT NULL," +
                "description text," +
                "budget real NOT NULL);";

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + database);
            Statement stmts = conn.createStatement();
            stmts.executeUpdate(transactionsTable);
            stmts.executeUpdate(vendorsTable);
            stmts.executeUpdate(vendorTagsTable);
            stmts.executeUpdate(vendorTaggingsTable);
            stmts.executeUpdate(vendorNamingsTable);
            stmts.executeUpdate(categoriesTable);
            stmts.close();

            System.out.println("Successfully initialized database");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addTransaction(String timestamp, double amount, String vendor) {
        try {
            ResultSet rs = getVendor(vendor);
            if(!rs.next()) {
                return false;
            }

            String insert = "INSERT INTO transactions (timestamp, amount, vendor) " +
                    "VALUES ( '" + timestamp + "', " + amount + ", " + rs.getInt("id") + " );";

            Statement statement = conn.createStatement();
            statement.executeUpdate(insert);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addVendor(String name, String nickname, String category) {
        try {
            ResultSet rs = getCategory(category);
            if(!rs.next()) {
                return false;
            }

            String insert = "INSERT INTO vendors (name, nickname, category) " +
                    "VALUES ( '" + name + "', '" + nickname + "', " + rs.getInt("id") + " );";

            Statement statement = conn.createStatement();
            statement.executeUpdate(insert);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addCategory(String name, String description, double budget) {
        String insert = "INSERT INTO categories (category, description, budget) " +
                "VALUES ( '" + name + "', '" + description + "', " + budget + " );";

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(insert);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet getVendor(String name) {
        String findName = "SELECT * " +
                "FROM vendors " +
                "WHERE name = '" + name + "';";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findName);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getCategory(String name) {
        String findName = "SELECT * " +
                "FROM categories " +
                "WHERE category = '" + name + "';";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findName);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printTransactions() {
        String findTransactions = "SELECT timestamp, amount, name " +
                "FROM transactions " +
                "LEFT OUTER JOIN vendors ON transactions.vendor = vendors.id;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findTransactions);

            System.out.println("Timestamp\t\t\tAmount\t\tVendor");
            while(rs.next()) {
                System.out.println(rs.getString("timestamp") + "\t" +
                "$" + rs.getDouble("amount") + "\t\t" +
                rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printVendors() {
        String findVendors = "SELECT name, nickname, categories.category as category " +
                "FROM vendors " +
                "LEFT OUTER JOIN categories ON vendors.category = categories.id;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findVendors);

            System.out.println("Name\t\tNickname\t\tCategory");
            while(rs.next()) {
                System.out.println(rs.getString("name") + "\t" +
                        rs.getString("nickname") + "\t\t" +
                        rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void printCategories() {
        String findCategories = "SELECT category, description, budget " +
                "FROM categories;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findCategories);

            System.out.println("Name\t\tBudget\tDescription");
            while(rs.next()) {
                System.out.println(rs.getString("category") + "\t" +
                        "$" + rs.getDouble("budget") + "\t" +
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
