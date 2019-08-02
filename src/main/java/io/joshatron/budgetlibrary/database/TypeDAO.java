package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TypeDAO {

    public static Type createType(Session session, String name, String description) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = new Type();
        type.setName(name);
        type.setDescription(description);
        session.save(type);

        tx.commit();

        return type;
    }

    public static void updateType(Session session, int typeId, String name, String description) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = session.get(Type.class, typeId);
        if(name != null) {
            type.setName(name);
        }
        if(description != null) {
            type.setDescription(description);
        }

        tx.commit();
    }

    public static void deleteType(Session session, int typeId) throws BudgetLibraryException {
        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = session.get(Type.class, typeId);
        session.delete(type);

        tx.commit();
    }

    public static List<Type> getTypes(Session session, String name, String description) throws BudgetLibraryException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("from Type t");
        if(isValid(name) || isValid(description)) {
            queryString.append(" where");

            if(isValid(name)) {
                queryString.append(" t.name=:name");
            }
            if(isValid(description)) {
                if(isValid(name)) {
                    queryString.append(" and");
                }
                queryString.append(" t.description=:description");
            }
        }


        Query<Type> query = session.createQuery(queryString.toString(), Type.class);
        if(isValid(name)) {
            query.setParameter("name", name);
        }
        if(isValid(description)) {
            query.setParameter("description", description);
        }

        return query.list();
    }

    private static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }
}
