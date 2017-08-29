package library;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Class to handler all inserting of data into the database
 * It will also do data validation as required
 */
public class InsertHandler {

    public static boolean addTransaction(Transaction transaction, Connection conn) {
        return false;
    }

    public static boolean addVendor(Vendor vendor, Connection conn) {
        return false;
    }

    public static boolean addCategory(Category category, Connection conn) {
        if(category.isValid()) {
            String insert = "INSERT INTO categories (category, description, budget) " +
                    "VALUES ( '" + category.getName() + "', '" + category.getDescription() + "', " + category.getBudget() + " );";

            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
