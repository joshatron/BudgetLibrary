package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Vendor;

import java.util.List;

public interface VendorDAO {

    void addVendor(Vendor vendor);
    void updateVendor(int vendorId, String oldName);
    void deleteVendor(int vendorId);
    void addVendorRawMapping(String vendor, String raw);

    //TODO: Add search vendors
    List<Vendor> getAllVendors();
    List<Vendor> getVendorsWithType(String type);
    Vendor getVendorFromName(String name);
    Vendor getVendorFromRaw(String raw);
    String[] getVendorNames();
    String[] getVendorTypes();
}
