package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAOSqlite implements VendorDAO {

    private Connection conn;

    public VendorDAOSqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Vendor createVendor(String name, Type type) throws BudgetLibraryException {
        if(name == null || name.isEmpty() || type == null || !type.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR);
        }
        if (!getVendors(name, type, null).isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.VENDOR_EXISTS);
        }

        String insertVendor = "INSERT INTO vendors (name,type) " +
                "VALUES (?,?);";

        try {
            //add vendor
            PreparedStatement stmt = conn.prepareStatement(insertVendor);
            stmt.setString(1, name);
            stmt.setInt(2, type.getId());
            stmt.executeUpdate();

            return getVendors(name, type, null).get(0);
        } catch (SQLException e) {
            throw new BudgetLibraryException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void createVendorRawMapping(Vendor vendor, String raw) throws BudgetLibraryException {
         if(vendor == null || !vendor.isValid() || raw == null || raw.isEmpty()) {
             throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR_MAPPING);
         }

        String insertVendor = "INSERT INTO vendor_namings (vendor, raw) " +
                "VALUES (?,?);";

         try {
            PreparedStatement stmt = conn.prepareStatement(insertVendor);
            stmt.setInt(1, vendor.getId());
            stmt.setString(2, raw);
            stmt.executeUpdate();
        } catch (SQLException e) {
             throw new BudgetLibraryException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void updateVendor(int vendorId, Vendor newVendor) throws BudgetLibraryException {
        if (!newVendor.isValid() || !vendorExists(vendorId)) {
            throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR);
        }

        String update = "UPDATE vendors " +
                "SET name = ?, type = ? " +
                "WHERE id = ?;";

        try {
            //add vendor
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, newVendor.getName());
            stmt.setInt(2, newVendor.getType().getId());
            stmt.setInt(3, vendorId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new BudgetLibraryException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public void deleteVendor(int vendorId) throws BudgetLibraryException {
        if(!vendorExists(vendorId)) {
            throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR);
        }
        if(vendorHasTransactions(vendorId)) {
            throw new BudgetLibraryException(ErrorCode.VENDOR_HAS_DEPENDANT_TRANSACTIONS);
        }

        String deleteRawNames = "DELETE FROM vendor_namings " +
                "WHERE vendor = ?;";
        String delete = "DELETE FROM vendors " +
                "WHERE id = ?;";

        try {
            //delete raw mappings
            PreparedStatement rawNamesStmt = conn.prepareStatement(deleteRawNames);
            rawNamesStmt.setInt(1, vendorId);
            rawNamesStmt.executeUpdate();

            //delete vendor
            PreparedStatement deleteStmt = conn.prepareStatement(delete);
            deleteStmt.setInt(1, vendorId);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            throw new BudgetLibraryException(ErrorCode.DATABASE_ERROR);
        }
    }

    private boolean vendorExists(int vendorId) {
        String search = "SELECT * FROM vendors " +
                "WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(search);
            stmt.setInt(1, vendorId);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean vendorHasTransactions(int vendorId) {
        String search = "SELECT * FROM transactions " +
                "WHERE vendor = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(search);
            stmt.setInt(1, vendorId);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
