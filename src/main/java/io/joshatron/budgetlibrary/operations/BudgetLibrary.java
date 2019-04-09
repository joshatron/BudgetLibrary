package io.joshatron.budgetlibrary.operations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
 * Wrapper for all the budget classes
 * The goal of this class is to be the universal api
 */
public class BudgetLibrary {
    private Session session;

    public BudgetLibrary() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        session = factory.openSession();
    }

    public BudgetLibrary(Session session) {
        this.session = session;
    }
}
