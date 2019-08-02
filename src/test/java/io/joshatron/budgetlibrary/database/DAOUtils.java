package io.joshatron.budgetlibrary.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DAOUtils {

    public static Session createSession() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        return factory.openSession();
    }

    public static void cleanUp(Session session) {
        Transaction tx = session.beginTransaction();
        session.createQuery("delete from Transaction").executeUpdate();
        session.createQuery("delete from Vendor").executeUpdate();
        session.createQuery("delete from Type").executeUpdate();
        session.createQuery("delete from Account").executeUpdate();
        tx.commit();
        session.close();
    }
}
