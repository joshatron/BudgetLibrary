package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;

import java.util.List;

public class VendorDAO {

    public static Vendor createVendor(Session session, String name, Type type) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setType(type);

        tx.commit();

        return vendor;
    }

    public static void createVendorRawMapping(Session session, int vendorId, String raw) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        vendor.addRawMapping(raw);

        tx.commit();
    }

    public static void updateVendor(Session session, int vendorId, String newName, Type newType) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        if(newName != null) {
            vendor.setName(newName);
        }
        if(newType != null) {
            vendor.setType(newType);
        }

        tx.commit();
    }

    public static void deleteVendor(Session session, int vendorId) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        session.delete(vendor);

        tx.commit();
    }

    public static void deleteVendorRawMapping(Session session, int vendorId, String raw) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        vendor.removeRawMapping(raw);

        tx.commit();
    }

    public static List<Vendor> getVendors(Session session, String name, Type type, String raw) throws BudgetLibraryException {
        return null;
    }
}
