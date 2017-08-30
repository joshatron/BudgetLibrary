package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Class to handler all inserting of data into the database
 * It will also do data validation as required
 */
public class InsertHandler {

    //todo: add duplicate testing
    public static boolean addTransaction(Transaction transaction, Connection conn) {
        if(transaction.isValid()) {
            int vendorID = getVendorID(transaction.getVendor(), conn);
            if(vendorID == -1) {
                addVendor(transaction.getVendor(), conn);
                vendorID = getVendorID(transaction.getVendor(), conn);
            }

            String insert = "INSERT INTO transactions (timestamp, amount, vendor) " +
                    "VALUES ( '" + transaction.getTimestamp().toString() + "', " +
                    transaction.getAmount() + ", " +
                    vendorID + " );";

            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private static int getVendorID(Vendor vendor, Connection conn) {
        if(vendor.isValid()) {
            String find = "SELECT id " +
                    "FROM vendors " +
                    "WHERE name = '" + vendor.getName() + "';";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(find);

                if(rs.next()) {
                    return rs.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    //todo: add duplicate testing
    public static boolean addVendor(Vendor vendor, Connection conn) {
        if(vendor.isValid()) {
            int categoryID = getCategoryID(vendor.getCategory(), conn);
            if(categoryID == -1) {
                addCategory(vendor.getCategory(), conn);
                categoryID = getCategoryID(vendor.getCategory(), conn);
            }

            String insertVendor = "INSERT INTO vendors (name, category) " +
                    "VALUES ( '" + vendor.getName() + "', " + categoryID + " );";

            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insertVendor);

                int vendorID = getVendorID(vendor, conn);
                for(String name : vendor.getRawNames()) {
                    String insertName = "INSERT INTO vendor_namings (vendor_id, name) " +
                            "VALUES ( " + vendorID + ", '" + name + "' );";
                    stmt.executeUpdate(insertName);
                }
                //todo: add adding tags
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private static int getCategoryID(Category category, Connection conn) {
        if(category.isValid()) {
            String find = "SELECT id " +
                    "FROM categories " +
                    "WHERE name = '" + category.getName() + "';";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(find);

                if(rs.next()) {
                    return rs.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    //todo: add duplicate testing
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
