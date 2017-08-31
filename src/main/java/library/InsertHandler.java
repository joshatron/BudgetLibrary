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

    //add transaction, adding vendor if necessary
    public static boolean addTransaction(Transaction transaction, Connection conn) {
        if(transaction.isValid()) {
            //transaction already in database
            if(getTransactionID(transaction, conn) != -1) {
                return false;
            }
            int vendorID = getVendorID(transaction.getVendor(), conn);
            //if vendor doesn't exist, add it
            if(vendorID == -1) {
                addVendor(transaction.getVendor(), conn);
                vendorID = getVendorID(transaction.getVendor(), conn);
            }

            String insert = "INSERT INTO transactions (timestamp, amount, vendor) " +
                    "VALUES ( " + transaction.getTimestamp() + ", " +
                    transaction.getAmount() + ", " +
                    vendorID + " );";

            try {
                //add transaction
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    //add vendor, adding category if necessary
    public static boolean addVendor(Vendor vendor, Connection conn) {
        System.out.println("vendor");
        if(vendor.isValid()) {
            //vendor already in database
            if(getVendorID(vendor, conn) != -1) {
                return false;
            }

            int categoryID = getCategoryID(vendor.getCategory(), conn);
            //category doesn't exist
            if(categoryID == -1) {
                addCategory(vendor.getCategory(), conn);
                categoryID = getCategoryID(vendor.getCategory(), conn);
            }

            String insertVendor = "INSERT INTO vendors (name, category) " +
                    "VALUES ( '" + vendor.getName() + "', " + categoryID + " );";

            try {
                //add vendor
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insertVendor);

                int vendorID = getVendorID(vendor, conn);

                //add vendor raw names
                if(vendor.getRawNames() != null) {
                    for (String name : vendor.getRawNames()) {
                        String insertName = "INSERT INTO vendor_namings (vendor_id, name) " +
                                "VALUES ( " + vendorID + ", '" + name + "' );";
                        stmt.executeUpdate(insertName);
                    }
                }

                //add vendor tags
                if(vendor.getTags() != null) {
                    for (String tag : vendor.getTags()) {
                        int tagID = getTagID(tag, conn);

                        if (tagID == -1) {
                            String insertTag = "INSERT INTO vendor_tags (name) " +
                                    "VALUES ( '" + tag + "' );";
                            stmt.executeUpdate(insertTag);
                            tagID = getTagID(tag, conn);
                        }

                        String insertTagging = "INSERT INTO vendor_taggings (vendor_id, tag_id) " +
                                "VALUES ( " + vendorID + ", " + tagID + " );";
                        stmt.executeUpdate(insertTagging);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    //add category
    public static boolean addCategory(Category category, Connection conn) {
        System.out.println("category");
        if(category.isValid()) {
            //category already in database
            if(getCategoryID(category, conn) != -1) {
                return false;
            }

            String insert = "INSERT INTO categories (name, description, budget) " +
                    "VALUES ( '" + category.getName() + "', '" + category.getDescription() + "', " + category.getBudget() + " );";

            try {
                //add category
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private static int getTransactionID(Transaction transaction, Connection conn) {
        if(transaction.isValid()) {
            int vendorID = getVendorID(transaction.getVendor(), conn);

            String find = "SELECT id " +
                    "FROM transactions " +
                    "WHERE timestamp = " + transaction.getTimestamp() + " AND " +
                    "vendor = " + vendorID + ";";

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

    //find tag id by searching for name
    //if none found, return -1
    private static int getTagID(String tag, Connection conn) {
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

    //find category id by searching for name
    //if none found, return -1
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
}
