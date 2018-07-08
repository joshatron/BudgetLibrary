package library.database;

import library.objects.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteUtils {
    public static int getTransactionID(Transaction transaction, Connection conn) {

        //find transaction id by searching for name
        //if none found, return -1
        if(transaction.isValid()) {
            int vendorID = getVendorID(transaction.getVendor().getName(), conn);

            String find = "SELECT id " +
                    "FROM transactions " +
                    "WHERE timestamp = " + transaction.getTimestamp().getTimestampLong() + " AND " +
                    "vendor = " + vendorID + " AND " +
                    "amount = " + transaction.getAmount() + ";";

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

    //find vendor id by searching for name
    //if none found, return -1
    public static int getVendorID(String name, Connection conn) {
        if(name != null) {
            String find = "SELECT id " +
                    "FROM vendors " +
                    "WHERE name = '" + name + "';";

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

    //find tag id by searching for name
    //if none found, return -1
    public static int getTagID(String tag, Connection conn) {
        String find = "SELECT id " +
                "FROM vendor_tags " +
                "WHERE  name = '" + tag + "';";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            if(rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
