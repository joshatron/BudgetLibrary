package library.database;

import library.objects.Category;
import library.objects.Vendor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VendorDAOSqlite implements VendorDAO {

    private Connection conn;
    private CategoryDAO categoryDAO;

    public VendorDAOSqlite(Connection conn, CategoryDAO categoryDAO) {
        this.conn = conn;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public void addVendor(Vendor vendor) {
        if (vendor.isValid()) {
            //vendor already in database
            if (getVendorFromName(vendor.getName()) != null) {
                return;
            }

            int categoryID = SqliteUtils.getCategoryID(vendor.getCategory().getName(), conn);

            if(categoryID == -1) {
                categoryDAO.addCategory(vendor.getCategory());
                categoryID = SqliteUtils.getCategoryID(vendor.getCategory().getName(), conn);
            }

            String insertVendor = "INSERT INTO vendors (name, category) " +
                    "VALUES ( '" + vendor.getName() + "', " + categoryID + " );";

            try {
                //add vendor
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insertVendor);

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                //add vendor raw names
                if (vendor.getRawNames() != null) {
                    for (String name : vendor.getRawNames()) {
                        String insertName = "INSERT INTO vendor_namings (vendor_id, name) " +
                                "VALUES ( " + vendorID + ", '" + name + "' );";
                        stmt.executeUpdate(insertName);
                    }
                }

                //add vendor tags
                if (vendor.getTags() != null) {
                    for (String tag : vendor.getTags()) {
                        int tagID = SqliteUtils.getTagID(tag, conn);

                        if (tagID == -1) {
                            String insertTag = "INSERT INTO vendor_tags (name) " +
                                    "VALUES ( '" + tag + "' );";
                            stmt.executeUpdate(insertTag);
                            tagID = SelectHandler.getTagID(tag, conn);
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
    public void updateVendor(Vendor vendor) {

    }

    @Override
    public void deleteVendor(Vendor vendor) {

    }

    @Override
    public ArrayList<Vendor> getAllVendors() {
        return null;
    }

    @Override
    public ArrayList<Vendor> getVendorsForCategory(Category category) {
        return null;
    }

    @Override
    public ArrayList<Vendor> getVendorsWithTag(String tag) {
        return null;
    }

    @Override
    public Vendor getVendorFromName(String name) {
        return null;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
}
