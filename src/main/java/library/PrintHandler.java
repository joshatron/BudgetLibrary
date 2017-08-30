package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Class to handle all printing of data from the database
 */
public class PrintHandler {

    public static void printTransactions(Connection conn) {
        String findTransactions = "SELECT timestamp, amount, name " +
                "FROM transactions " +
                "LEFT OUTER JOIN vendors ON transactions.vendor = vendors.id;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findTransactions);

            System.out.println("Timestamp                Amount          Vendor");
            while(rs.next()) {
                String timestamp = rs.getString("timestamp");
                double amount = rs.getDouble("amount");
                String vendor = rs.getString("name");
                System.out.println(String.format("%-25s$%-15.2f%s", timestamp, amount, vendor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printVendors(Connection conn) {
        String findVendors = "SELECT vendors.name, categories.name as category " +
                "FROM vendors " +
                "LEFT OUTER JOIN categories ON vendors.category = categories.id;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findVendors);

            System.out.println("Vendor              Category");
            while(rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                System.out.println(String.format("%-20s%s", name, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printCategories(Connection conn) {
        String findCategories = "SELECT name, description, budget " +
                "FROM categories;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(findCategories);

            System.out.println("Category            Budget          Description");
            while(rs.next()) {
                String category = rs.getString("name");
                double budget = rs.getDouble("budget");
                String description = rs.getString("description");
                System.out.println(String.format("%-20s$%-15.2f%s", category, budget, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
