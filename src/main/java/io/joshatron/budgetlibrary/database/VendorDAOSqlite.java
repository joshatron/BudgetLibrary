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

            String insertVendor = "INSERT INTO vendors (name,type) " +
                    "VALUES (?,?);";

            try {
                //add vendor
                PreparedStatement stmt = conn.prepareStatement(insertVendor);
                stmt.setString(1, vendor.getName());
                stmt.setString(2, vendor.getType());
                stmt.executeUpdate();

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
                    "SET name = ?, type = ? " +
                    "WHERE name = ?;";

            try {
                //add vendor
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, vendor.getName());
                stmt.setString(2, vendor.getType());
                stmt.setString(3, oldName);
                stmt.executeUpdate();

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);
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
            String delete = "DELETE FROM vendors " +
                    "WHERE name = ?;";

            try {
                //delete raw mappings
                PreparedStatement rawNamesStmt = conn.prepareStatement(deleteRawNames);
                rawNamesStmt.setInt(1, vendorID);
                rawNamesStmt.executeUpdate();

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

        String find = "SELECT name, type " +
                "FROM vendors;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setType(rs.getString("type"));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public ArrayList<Vendor> getVendorsWithType(String type) {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        String find = "SELECT name, type " +
                "FROM vendors " +
                "WHERE type = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setType(rs.getString("type"));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public Vendor getVendorFromName(String name) {
        String find = "SELECT name, type " +
                "FROM vendors " +
                "WHERE name = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setName(rs.getString("name"));
                vendor.setType(rs.getString("type"));

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
                Vendor vendor = getVendorFromName(rs.getString("vendor"));
                return vendor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String[] getVendorNames() {
        Set<String> vendors = new HashSet<>();

        String find = "SELECT name " +
                "FROM vendors;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vendors.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors.toArray(new String[vendors.size()]);
    }

    @Override
    public String[] getVendorTypes() {
        Set<String> vendors = new HashSet<>();

        String find = "SELECT type " +
                "FROM vendors;";

        try {
            PreparedStatement stmt = conn.prepareStatement(find);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vendors.add(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors.toArray(new String[vendors.size()]);
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
