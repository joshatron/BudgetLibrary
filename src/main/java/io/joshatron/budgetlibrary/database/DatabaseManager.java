package io.joshatron.budgetlibrary.database;

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
                "timestamp integer NOT NULL," +
                "amount integer NOT NULL," +
                "account text," +
                "vendor integer NOT NULL);";

        String vendorsTable = "CREATE TABLE IF NOT EXISTS vendors (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "name text NOT NULL," +
                "type text NOT NULL);";

        String vendorNamingsTable = "CREATE TABLE IF NOT EXISTS vendor_namings (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "vendor text NOT NULL," +
                "raw text NOT NULL);";

        try {
            Statement stmts = conn.createStatement();
            stmts.executeUpdate(transactionsTable);
            stmts.executeUpdate(vendorsTable);
            stmts.executeUpdate(vendorNamingsTable);
            stmts.close();

            System.out.println("Successfully initialized database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
