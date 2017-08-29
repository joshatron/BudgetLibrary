package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void printVendors(Connection conn) {
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

    public static void printCategories(Connection conn) {
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
