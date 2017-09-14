package library.database;

import library.objects.Category;
import library.objects.Vendor;

import java.util.ArrayList;

public interface VendorDAO {

    public void addVendor(Vendor vendor);
    public void updateVendor(Vendor vendor);
    public void deleteVendor(Vendor vendor);

    public ArrayList<Vendor> getAllVendors();
    public ArrayList<Vendor> getVendorsForCategory(Category category);
    public ArrayList<Vendor> getVendorsWithTag(String tag);
    public Vendor getVendorFromName(String name);
}
