package library.database;

import library.objects.Vendor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class VendorDAOSqlite implements VendorDAO {

    private Connection conn;

    public VendorDAOSqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addVendor(Vendor vendor) {
        if (vendor.isValid()) {
            //vendor already in database
            if (getVendorFromName(vendor.getName()) != null) {
                return;
            }

            String insertVendor = "INSERT INTO vendors (name) " +
                    "VALUES ( '" + vendor.getName() + "' );";

            try {
                //add vendor
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insertVendor);

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                //add vendor tags
                if (vendor.getTags() != null) {
                    for (String tag : vendor.getTags()) {
                        int tagID = SqliteUtils.getTagID(tag, conn);

                        if (tagID == -1) {
                            String insertTag = "INSERT INTO vendor_tags (name) " +
                                    "VALUES ( '" + tag + "' );";
                            stmt.executeUpdate(insertTag);
                            tagID = SqliteUtils.getTagID(tag, conn);
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
    }

    @Override
    public void updateVendor(Vendor vendor, String oldName) {
        if (vendor.isValid()) {
            //vendor is not in database
            if (getVendorFromName(vendor.getName()) == null) {
                return;
            }

            String update = "UPDATE vendors " +
                    "SET name = '" + vendor.getName() + "' " +
                    "WHERE name = '" + oldName + "';";

            try {
                //add vendor
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(update);

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                //delete old taggings
                String deleteTaggings = "DELETE FROM vendor_taggings " +
                        "WHERE vendor_id = " + vendorID + ";";

                stmt.executeUpdate(deleteTaggings);
                //add vendor tags
                if (vendor.getTags() != null) {
                    for (String tag : vendor.getTags()) {
                        int tagID = SqliteUtils.getTagID(tag, conn);

                        if (tagID == -1) {
                            String insertTag = "INSERT INTO vendor_tags (name) " +
                                    "VALUES ( '" + tag + "' );";
                            stmt.executeUpdate(insertTag);
                            tagID = SqliteUtils.getTagID(tag, conn);
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
    }

    //TODO: needs to be written to not leave abandoned transactions
    @Override
    public void deleteVendor(String name) {
        if(name != null) {
            int vendorID = SqliteUtils.getVendorID(name, conn);

            String deleteRawNames = "DELETE FROM vendor_namings " +
                    "WHERE vendor_id = " + vendorID + ";";
            String deleteTaggings = "DELETE FROM vendor_taggings " +
                    "WHERE vendor_id = " + vendorID + ";";
            String delete = "DELETE FROM vendors " +
                    "WHERE name = '" + name + "';";

            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(deleteRawNames);
                stmt.executeUpdate(deleteTaggings);
                stmt.executeUpdate(delete);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addVendorRawMapping(String vendor, String raw) {

        try {
            String insertVendor = "INSERT INTO vendor_namings (vendor, raw) " +
                    "VALUES ( '" + vendor + "', '" + raw + "' );";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertVendor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        String find = "SELECT name " +
                "FROM vendors;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public ArrayList<Vendor> getVendorsWithTag(String tag) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        int tagID = SqliteUtils.getTagID(tag, conn);

        if(tagID == -1) {
            return null;
        }

        String find = "SELECT vendors.name as name " +
                "FROM vendor_taggings " +
                "WHERE tag_id = " + tagID + " " +
                "LEFT OUTER JOIN vendors on vendor_taggings.vendor_id = vendors.id;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public Vendor getVendorFromName(String name) {
        String find = "SELECT name " +
                "FROM vendors " +
                "WHERE name = '" + name + "';";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));

                return vendor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Vendor getVendorFromRaw(String name) {
        String find = "SELECT vendor " +
                "FROM vendor_namings " +
                "WHERE raw = '" + name + "';";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("vendor"));
                vendor.setTags(getVendorTags(vendor.getName()));

                return vendor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String[] getVendorsFromRaw() {
        Set<String> vendors = new HashSet<>();

        String find = "SELECT vendor " +
                "FROM vendor_namings;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                vendors.add(rs.getString("vendor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors.toArray(new String[vendors.size()]);
    }

    private ArrayList<String> getVendorTags(String name) {
        ArrayList<String> tags = new ArrayList<String>();

        int vendorID = SqliteUtils.getVendorID(name, conn);

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

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
