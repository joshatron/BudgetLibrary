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

            int categoryID = SqliteUtils.getCategoryID(vendor.getCategory().getName(), conn);

            if(categoryID == -1) {
                categoryDAO.addCategory(vendor.getCategory());
                categoryID = SqliteUtils.getCategoryID(vendor.getCategory().getName(), conn);
            }

            String update = "UPDATE vendors " +
                    "SET name = '" + vendor.getName() + "', category = " + categoryID + " " +
                    "WHERE name = '" + oldName + "';";

            try {
                //add vendor
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(update);

                int vendorID = SqliteUtils.getVendorID(vendor.getName(), conn);

                //delete old raw names
                String deleteRawNames = "DELETE FROM vendor_namings " +
                        "WHERE vendor_id = " + vendorID + ";";

                stmt.executeUpdate(deleteRawNames);

                //add vendor raw names
                if (vendor.getRawNames() != null) {
                    for (String name : vendor.getRawNames()) {
                        String insertName = "INSERT INTO vendor_namings (vendor_id, name) " +
                                "VALUES ( " + vendorID + ", '" + name + "' );";
                        stmt.executeUpdate(insertName);
                    }
                }

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
    public ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        String find = "SELECT id, name, categories.name as category_name " +
                "FROM vendors " +
                "LEFT OUTER JOIN categories on vendors.category = categories.id;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(categoryDAO.getCategoryFromName(rs.getString("category_name")));
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));
                vendor.setRawNames(getVendorRawNames(vendor.getName()));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public ArrayList<Vendor> getVendorsForCategory(Category category) {
        if(!category.isValid()) {
            return null;
        }

        ArrayList<Vendor> vendors = new ArrayList<Vendor>();

        int categoryID = SqliteUtils.getCategoryID(category.getName(), conn);

        if(categoryID == -1) {
            return null;
        }

        String find = "SELECT name " +
                "FROM vendors " +
                "WHERE category = " + categoryID + ";";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(category);
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));
                vendor.setRawNames(getVendorRawNames(vendor.getName()));

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

        String find = "SELECT vendors.name as name, categories.name as category_name " +
                "FROM vendor_taggings " +
                "WHERE tag_id = " + tagID + " " +
                "LEFT OUTER JOIN vendors on vendor_taggings.vendor_id = vendors.id " +
                "LEFT OUTER JOIN categories on vendors.category = categories.id;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(categoryDAO.getCategoryFromName(rs.getString("category_name")));
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));
                vendor.setRawNames(getVendorRawNames(vendor.getName()));

                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    @Override
    public Vendor getVendorFromName(String name) {
        String find = "SELECT name, categories.name as category_name " +
                "FROM vendors " +
                "WHERE name = '" + name + "' " +
                "LEFT OUTER JOIN categories on vendors.category = categories.id;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(find);

            if (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setCategory(categoryDAO.getCategoryFromName(rs.getString("category_name")));
                vendor.setName(rs.getString("name"));
                vendor.setTags(getVendorTags(vendor.getName()));
                vendor.setRawNames(getVendorRawNames(vendor.getName()));

                return vendor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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

    private ArrayList<String> getVendorRawNames(String name) {
        ArrayList<String> rawNames = new ArrayList<String>();

        int vendorID = SqliteUtils.getVendorID(name, conn);

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
