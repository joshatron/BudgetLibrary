package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;

import java.util.List;

public interface VendorDAO {

    int addVendor(Vendor vendor);
    void updateVendor(int vendorId, String oldName);
    void deleteVendor(int vendorId);
    void addVendorRawMapping(String vendor, String raw);

    List<Vendor> searchVendors(String name, Type type);
    Vendor getVendorFromId(int vendorId);
    Vendor getVendorFromName(String name);
    Vendor getVendorFromRaw(String raw);
    String[] getVendorNames();
    String[] getVendorTypes();
}
