package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.objects.Transaction;

import java.sql.*;

public class SqliteUtils {
    public static int getTransactionID(Transaction transaction, Connection conn) {

        //find transaction id by searching for name
        //if none found, return -1
        if(transaction.isValid()) {
            int vendorID = getVendorID(transaction.getVendor().getName(), conn);

            String find = "SELECT id " +
                    "FROM transactions " +
                    "WHERE timestamp = ? AND " +
                    "vendor = ? AND " +
                    "amount = ?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(find);
                stmt.setLong(1, transaction.getTimestamp().getTimestampLong());
                stmt.setInt(2, vendorID);
                stmt.setInt(3, transaction.getAmount());
                ResultSet rs = stmt.executeQuery();

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
                    "WHERE name = ?;";

            try {
                PreparedStatement stmt = conn.prepareStatement(find);
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();

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
                "WHERE  name = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setString(1, tag);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
