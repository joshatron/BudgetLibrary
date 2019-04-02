package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VendorDAOSqlite implements VendorDAO {

    private Connection conn;

    public VendorDAOSqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Vendor createVendor(String name, Type type) throws BudgetLibraryException {
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
        return null;
    }

    @Override
    public void createVendorRawMapping(Vendor vendor, String raw) throws BudgetLibraryException {
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
    public void updateVendor(int vendorId, Vendor newVendor) throws BudgetLibraryException {
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
    public void deleteVendor(int vendorId) throws BudgetLibraryException {
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
    public List<Vendor> getVendors(String name, Type type, String raw) throws BudgetLibraryException {
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
        return null;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
