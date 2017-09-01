package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Class to handle retrieving data from the database
 */
public class SelectHandler {

    public static ArrayList<Transaction> getTransactions(Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String find = "SELECT timestamp, amount, vendor " +
                "FROM transactions;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTimestamp(new Timestamp(rs.getLong("timestamp")));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setVendor(getVendorFromID(rs.getInt("vendor"), conn));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public  static ArrayList<Transaction> getTransactionsInTimeRange(Timestamp start, Timestamp end, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String find = "SELECT timestamp, amount, vendor " +
                "FROM transactions " +
                "WHERE timestamp > " + start.getTimestampLong() + " AND " +
                "timestamp < " + end.getTimestampLong() + ";";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTimestamp(new Timestamp(rs.getLong("timestamp")));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setVendor(getVendorFromID(rs.getInt("vendor"), conn));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public static ArrayList<Transaction> getTransactionsForVendor(Vendor vendor, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    public static ArrayList<Transaction> getTransactionsForCategory(Category category, Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        return transactions;
    }

    private static Vendor getVendorFromID(int vendorID, Connection conn) {
        String find = "SELECT id, name, category " +
                "FROM vendors " +
                "WHERE id = " + vendorID + ";";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(getCategoryFromID(rs.getInt("category"), conn));
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor, conn));
                vendor.setRawNames(getVendorRawNames(vendor, conn));

                return vendor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Vendor> getVendors(Connection conn) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        String find = "SELECT id, name, category " +
                "FROM vendors;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(getCategoryFromID(rs.getInt("category"), conn));
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor, conn));
                vendor.setRawNames(getVendorRawNames(vendor, conn));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    private static Category getCategoryFromID(int categoryID, Connection conn) {
        String find = "SELECT name, description, budget " +
                "FROM categories " +
                "WHERE id = " + categoryID + ";";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(find);

            if (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setBudget(rs.getDouble("budget"));
                category.setDescription(rs.getString("description"));

                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static ArrayList<Vendor> getVendorsForCategory(Category category, Connection conn) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        int categoryID = getCategoryID(category, conn);

        if(categoryID == -1) {
            return null;
        }

        String find = "SELECT id, name " +
                "FROM vendors " +
                "WHERE category = " + categoryID + ";";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(category);
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor, conn));
                vendor.setRawNames(getVendorRawNames(vendor, conn));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    private static ArrayList<String> getVendorTags(Vendor vendor, Connection conn) {
        ArrayList<String> tags = new ArrayList<String>();

        int vendorID = getVendorID(vendor, conn);

        if(vendorID == -1) {
            return null;
        }

        String findTags = "SELECT vendor_tags.name as name " +
                "FROM vendor_taggings " +
                "WHERE vendor_id = " + vendorID + " " +
                "LEFT OUTER JOIN vendor_tags ON vendor_taggings.tag_id = vendor_tags.id;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(findTags);

            while (rs.next()) {
                tags.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tags;
    }

    private static ArrayList<String> getVendorRawNames(Vendor vendor, Connection conn) {
        ArrayList<String> rawNames = new ArrayList<String>();

        int vendorID = getVendorID(vendor, conn);

        if(vendorID == -1) {
            return null;
        }

        String findRawNames = "SELECT name " +
                "FROM vendor_namings " +
                "WHERE vendor_id = " + vendorID + ";";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(findRawNames);

            while (rs.next()) {
                rawNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rawNames;
    }

    public static ArrayList<Category> getCategories(Connection conn) {
        ArrayList<Category> categories = new ArrayList<Category>();

        String find = "SELECT name, description, budget " +
                "FROM categories;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(find);

            while (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setBudget(rs.getDouble("budget"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public static Category getCategory(String name, Connection conn) {
        String find = "SELECT name, description, budget " +
                "FROM categories " +
                "WHERE name = '" + name + "';";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(find);

            if (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setBudget(rs.getDouble("budget"));
                category.setDescription(rs.getString("description"));

                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getTransactionID(Transaction transaction, Connection conn) {
        if(transaction.isValid()) {
            int vendorID = getVendorID(transaction.getVendor(), conn);

            String find = "SELECT id " +
                    "FROM transactions " +
                    "WHERE timestamp = " + transaction.getTimestamp().getTimestampLong() + " AND " +
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
    public static int getVendorID(Vendor vendor, Connection conn) {
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

    //find category id by searching for name
    //if none found, return -1
    public static int getCategoryID(Category category, Connection conn) {
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
