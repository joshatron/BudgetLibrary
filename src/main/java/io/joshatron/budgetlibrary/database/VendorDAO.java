package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;

import java.util.List;

public interface VendorDAO {

    Vendor createVendor(String name, Type type) throws BudgetLibraryException;
    void createVendorRawMapping(Vendor vendor, String raw) throws BudgetLibraryException;
    void updateVendor(int vendorId, Vendor newVendor) throws BudgetLibraryException;
    void deleteVendor(int vendorId) throws BudgetLibraryException;

    List<Vendor> getVendors(String name, Type type, String raw) throws BudgetLibraryException;
}
