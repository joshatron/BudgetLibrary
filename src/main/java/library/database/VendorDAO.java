package library.database;

import library.objects.Category;
import library.objects.Vendor;

import java.util.ArrayList;

public interface VendorDAO {

    void addVendor(Vendor vendor);
    void updateVendor(Vendor vendor, String oldName);
    void deleteVendor(String name);

    ArrayList<Vendor> getAllVendors();
    ArrayList<Vendor> getVendorsForCategory(Category category);
    ArrayList<Vendor> getVendorsWithTag(String tag);
    Vendor getVendorFromName(String name);
}
