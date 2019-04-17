package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.imports.ImportDAO;
import io.joshatron.budgetlibrary.imports.ImportDAOAlliant;
import io.joshatron.budgetlibrary.imports.ImportDAOCiti;
import io.joshatron.budgetlibrary.imports.TransactionImport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

/*
 * Wrapper for all the budget classes
 * The goal of this class is to be the universal api
 */
public class BudgetLibrary {
    private Session session;
    private List<ImportDAO> importers;

    public BudgetLibrary() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        session = factory.openSession();
        initializeImporters();
    }

    public BudgetLibrary(Session session) {
        this.session = session;
        initializeImporters();
    }

    private void initializeImporters() {
        importers = new ArrayList<>();
        importers.add(new ImportDAOCiti());
        importers.add(new ImportDAOAlliant());
    }

    public void createTransaction(Timestamp timestamp, Money amount, Vendor vendor, Account account) {
    }

    public void createVendor(String name, Type type) {
    }

    public Vendor getVendorFromRaw(String raw) {
        return null;
    }

    public void createType(String name, String description) {
    }

    public void createAccount(String name, String description) {
    }

    public void importTransactions(String fileName, String importerName) {
        for(ImportDAO importer : importers) {
            if(importer.getName().equalsIgnoreCase(importerName)) {
                List<TransactionImport> transactionImports = importer.addTransactions(fileName);
                //Add transactions
                break;
            }
        }
    }
}
