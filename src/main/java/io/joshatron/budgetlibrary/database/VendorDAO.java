package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class VendorDAO {

    public static Vendor createVendor(Session session, String name, Type type) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);
        DAOValidator.validateType(type);

        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setType(type);
        session.save(vendor);

        tx.commit();

        return vendor;
    }

    public static void createVendorRawMapping(Session session, long vendorId, String raw) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(raw);

        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        DAOValidator.validateVendor(vendor);
        DAOValidator.validateStringNotInList(raw, vendor.getRawMappings());
        vendor.addRawMapping(raw);

        tx.commit();
    }

    public static void updateVendor(Session session, long vendorId, String newName, Type newType) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        DAOValidator.validateVendor(vendor);

        if(newName != null && !newName.isEmpty()) {
            vendor.setName(newName);
        }
        if(newType != null && newType.isValid()) {
            vendor.setType(newType);
        }

        tx.commit();
    }

    public static void deleteVendor(Session session, long vendorId) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        DAOValidator.validateVendor(vendor);
        session.delete(vendor);

        tx.commit();
    }

    public static void deleteVendorRawMapping(Session session, long vendorId, String raw) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = session.get(Vendor.class, vendorId);
        DAOValidator.validateVendor(vendor);
        DAOValidator.validateStringInList(raw, vendor.getRawMappings());
        vendor.removeRawMapping(raw);

        tx.commit();
    }

    public static List<Vendor> getAllVendors(Session session) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        Query<Vendor> query = session.createQuery("from Vendor v", Vendor.class);
        return query.list();
    }

    public static Vendor getVendorByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Vendor> query = session.createQuery("from Vendor v where v.name=:name", Vendor.class);
        query.setParameter("name", name);
        List<Vendor> vendors = query.list();

        DAOValidator.validateOnlyOneResult(vendors);
        return vendors.get(0);
    }

    public static List<Vendor> getVendorsByType(Session session, Type type) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateType(type);

        Query<Vendor> query = session.createQuery("from Vendor v where v.type=:type", Vendor.class);
        query.setParameter("type", type);

        return query.list();
    }

    public static Vendor getVendorByRawMapping(Session session, String raw) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(raw);

        Query<Vendor> query = session.createQuery("from Vendor v where :raw in elements(v.rawMappings)", Vendor.class);
        query.setParameter("raw", raw);
        List<Vendor> vendors = query.list();

        DAOValidator.validateOnlyOneResult(vendors);
        return vendors.get(0);
    }

    public static List<Vendor> searchVendorsByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Vendor> query = session.createQuery("from Vendor v where v.name like :name", Vendor.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }
}
