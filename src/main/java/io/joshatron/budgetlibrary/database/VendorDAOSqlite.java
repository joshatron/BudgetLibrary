package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.objects.Vendor;

import java.sql.*;
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
                    "VALUES (?);";

            try {
                //add vendor
                PreparedStatement stmt = conn.prepareStatement(insertVendor);
                stmt.setString(1, vendor.getName());
                stmt.executeUpdate();

                //add vendor tags
                addVendorTags(vendor);
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
                    "SET name = ? " +
                    "WHERE name = ?;";

            try {
                //add vendor
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, vendor.getName());
                stmt.setString(2, oldName);
                stmt.executeUpdate();

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                //delete old taggings
                String deleteTaggings = "DELETE FROM vendor_taggings " +
                        "WHERE vendor_id = ?;";

                PreparedStatement deleteStmt = conn.prepareStatement(deleteTaggings);
                deleteStmt.setInt(1, vendorID);
                deleteStmt.executeUpdate();
                //add vendor tags
                addVendorTags(vendor);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void addVendorTags(Vendor vendor) {
        if (vendor.getTags() != null) {
            try {
                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                String insertTag = "INSERT INTO vendor_tags (name) " +
                        "VALUES (?);";
                PreparedStatement tagStmt = conn.prepareStatement(insertTag);

                String insertTagging = "INSERT INTO vendor_taggings (vendor_id, tag_id) " +
                        "VALUES (?,?);";
                PreparedStatement taggingStmt = conn.prepareStatement(insertTagging);
                for (String tag : vendor.getTags()) {
                    int tagID = SqliteUtils.getTagID(tag, conn);

                    if (tagID == -1) {
                        tagStmt.setString(1, tag);
                        tagStmt.executeUpdate();
                        tagID = SqliteUtils.getTagID(tag, conn);
                    }

                    taggingStmt.setInt(1, vendorID);
                    taggingStmt.setInt(2, tagID);
                    taggingStmt.executeUpdate();
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
                    "WHERE vendor_id = ?;";
            String deleteTaggings = "DELETE FROM vendor_taggings " +
                    "WHERE vendor_id = ?;";
            String delete = "DELETE FROM vendors " +
                    "WHERE name = ?;";

            try {
                //delete raw mappings
                PreparedStatement rawNamesStmt = conn.prepareStatement(deleteRawNames);
                rawNamesStmt.setInt(1, vendorID);
                rawNamesStmt.executeUpdate();

                //delete taggings
                PreparedStatement taggingStmt = conn.prepareStatement(deleteTaggings);
                taggingStmt.setInt(1, vendorID);
                taggingStmt.executeUpdate();

                //delete vendor
                PreparedStatement deleteStmt = conn.prepareStatement(delete);
                deleteStmt.setString(1, name);
                deleteStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addVendorRawMapping(String vendor, String raw) {

        try {
            String insertVendor = "INSERT INTO vendor_namings (vendor, raw) " +
                    "VALUES (?,?);";

            PreparedStatement stmt = conn.prepareStatement(insertVendor);
            stmt.setString(1, vendor);
            stmt.setString(2, raw);
            stmt.executeUpdate();
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
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

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
                "LEFT OUTER JOIN vendors on vendor_taggings.vendor_id = vendors.id " +
                "WHERE tag_id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setInt(1, tagID);
            ResultSet rs = stmt.executeQuery();

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
                "WHERE name = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

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
                "WHERE raw = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

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
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

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
                "LEFT OUTER JOIN vendor_tags ON vendor_taggings.tag_id = vendor_tags.id " +
                "WHERE vendor_id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(findTags);
            stmt.setInt(1, vendorID);
            ResultSet rs = stmt.executeQuery();

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
