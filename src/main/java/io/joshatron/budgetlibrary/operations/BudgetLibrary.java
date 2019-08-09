package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.database.AccountDAO;
import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.database.TypeDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
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
public abstract class BudgetLibrary {
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

    public void createTransaction(Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException {
        if(timestamp == null || amount == null || vendor == null || !vendor.isValid() || account == null || !account.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TRANSACTION);
        }

        TransactionDAO.createTransaction(session, timestamp, amount, vendor, account);
    }

    public List<Transaction> getTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) throws BudgetLibraryException {
        return TransactionDAO.searchTransactions(session, start, end, min, max, vendor, account);
    }

    public void createVendor(String name, Type type) throws BudgetLibraryException {
        if(session == null || name == null || name.isEmpty() || type == null || !type.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR);
        }

        VendorDAO.createVendor(session, name, type);
    }

    public List<Vendor> getVendors(String name, Type type) throws BudgetLibraryException {
        return VendorDAO.getAllVendors(session);
    }

    protected abstract Vendor getVendorFromRaw(String raw) throws BudgetLibraryException;

    public void createType(String name, String description) throws BudgetLibraryException {
        if(name == null || name.isEmpty() || (description != null && description.isEmpty())) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TYPE);
        }

        TypeDAO.createType(session, name, description);
    }

    public List<Type> getTypes(String name, String description) throws BudgetLibraryException {
        return TypeDAO.getAllTypes(session);
    }

    public void createAccount(String name, String bank, String description) throws BudgetLibraryException {
        if(name == null || name.isEmpty() || bank == null || bank.isEmpty() || (description != null && description.isEmpty())) {
            throw new BudgetLibraryException(ErrorCode.INVALID_ACCOUNT);
        }

        AccountDAO.createAccount(session, name, bank, description);
    }

    public List<Account> getAccounts(String name, String bank, String description) throws BudgetLibraryException {
        return AccountDAO.getAllAccounts(session);
    }

    public void importTransactions(String fileName, Account account) throws BudgetLibraryException {
        for(ImportDAO importer : importers) {
            if(importer.getName().equalsIgnoreCase(account.getBank())) {
                List<TransactionImport> transactionImports = importer.addTransactions(fileName);

                for(TransactionImport transactionImport : transactionImports) {
                    createTransaction(transactionImport.getTimestamp(), transactionImport.getAmount(), getVendorFromRaw(transactionImport.getRawVendor()), account);
                }

                break;
            }
        }
    }

    public Session getSession() {
        return session;
    }
}
