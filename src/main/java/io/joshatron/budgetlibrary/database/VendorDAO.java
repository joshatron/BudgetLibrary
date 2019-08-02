package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class VendorDAO {

    public static Vendor createVendor(Session session, String name, Type type) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setType(type);
        session.save(vendor);

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
        StringBuilder queryString = new StringBuilder();
        queryString.append("from Vendor v inner join v.type as t");
        if(isValid(name) || isValid(raw)) {
            queryString.append(" where");

            if(isValid(name)) {
                queryString.append(" v.name=:name");
            }
            if(type.isValid()) {
                if(isValid(name)) {
                    queryString.append(" and");
                }
                queryString.append(" t.id=:id");
            }
        }


        Query<Vendor> query = session.createQuery(queryString.toString(), Vendor.class);
        if(isValid(name)) {
            query.setParameter("name", name);
        }
        if(type.isValid()) {
            query.setParameter("id", type.getId());
        }

        return query.list();
    }

    private static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }
}
