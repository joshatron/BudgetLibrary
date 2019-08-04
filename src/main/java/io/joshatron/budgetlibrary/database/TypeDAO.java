package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TypeDAO {

    public static Type createType(Session session, String name, String description) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty() || description == null || description.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = new Type();
        type.setName(name);
        type.setDescription(description);
        session.save(type);

        tx.commit();

        return type;
    }

    public static void updateType(Session session, long typeId, String name, String description) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

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

    public static void deleteType(Session session, long typeId) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

        org.hibernate.Transaction tx = session.beginTransaction();

        Type type = session.get(Type.class, typeId);
        session.delete(type);

        tx.commit();
    }

    public static List<Type> getAllTypes(Session session) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }

        Query<Type> query = session.createQuery("from Type t", Type.class);
        return query.list();
    }

    public static Type getTypeByName(Session session, String name) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Type> query = session.createQuery("from Type a where a.name=:name", Type.class);
        query.setParameter("name", name);
        List<Type> types = query.list();

        if(types.size() == 1) {
            return types.get(0);
        }
        else if(types.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.NO_RESULT_FOUND);
        }
        else {
            throw new BudgetLibraryException(ErrorCode.TOO_MANY_RESULTS_FOUND);
        }
    }

    public static List<Type> searchTypesByName(Session session, String name) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
        if(name == null || name.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }

        Query<Type> query = session.createQuery("from Type a where a.name like :name", Type.class);
        query.setParameter("name", "%" + name + "%");

        return query.list();
    }
}
