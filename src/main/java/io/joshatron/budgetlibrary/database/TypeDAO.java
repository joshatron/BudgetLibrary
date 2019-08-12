package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TypeDAO {

    public static Type createType(Session session, String name, String description) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);
        DAOValidator.validateString(description);

        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = new Type();
        type.setName(name);
        type.setDescription(description);
        session.save(type);

        tx.commit();

        return type;
    }

    public static void updateType(Session session, long typeId, String newName, String newDescription) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        if(newName != null) {
            DAOValidator.validateString(newName);
        }
        if(newDescription != null) {
            DAOValidator.validateString(newDescription);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = session.get(Type.class, typeId);
        DAOValidator.validateType(type);

        if(newName != null) {
            type.setName(newName);
        }
        if(newDescription != null) {
            type.setDescription(newDescription);
        }

        tx.commit();
    }

    public static void deleteType(Session session, long typeId) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = session.get(Type.class, typeId);
        DAOValidator.validateType(type);
        session.delete(type);

        tx.commit();
    }

    public static List<Type> getAllTypes(Session session) throws BudgetLibraryException {
        DAOValidator.validateSession(session);

        Query<Type> query = session.createQuery("from Type t order by t.name asc", Type.class);
        return query.list();
    }

    public static Type getTypeByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Type> query = session.createQuery("from Type t where t.name=:name", Type.class);
        query.setParameter("name", name);
        List<Type> types = query.list();

        DAOValidator.validateOnlyOneResult(types);
        return types.get(0);
    }

    public static List<Type> searchTypesByName(Session session, String name) throws BudgetLibraryException {
        DAOValidator.validateSession(session);
        DAOValidator.validateString(name);

        Query<Type> query = session.createQuery("from Type t where t.name like :name order by t.name asc", Type.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }
}
