package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public static Connection getConnection() {
        String database = "budget.db";

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + database);
            initializeDatabase(conn);

            return conn;
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }

        return null;
    }

    private static void initializeDatabase(Connection conn) {
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
                "name text NOT NULL);";

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
                "name text NOT NULL," +
                "description text," +
                "budget real NOT NULL);";

        try {
            Statement stmts = conn.createStatement();
            stmts.executeUpdate(transactionsTable);
            stmts.executeUpdate(vendorsTable);
            stmts.executeUpdate(vendorTagsTable);
            stmts.executeUpdate(vendorTaggingsTable);
            stmts.executeUpdate(vendorNamingsTable);
            stmts.executeUpdate(categoriesTable);
            stmts.close();

            System.out.println("Successfully initialized database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
