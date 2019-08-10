package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ImportManager {
    private Session session;
    private List<ImportDAO> importers;

    public ImportManager(Session session) {
        this.session = session;
        initializeImporters();
    }

    private void initializeImporters() {
        importers = new ArrayList<>();
        importers.add(new ImportDAOCiti());
        importers.add(new ImportDAOAlliant());
    }

    public void importTransactions(String fileName, Account account) throws BudgetLibraryException {
        for(ImportDAO importer : importers) {
            if(importer.getName().equalsIgnoreCase(account.getBank())) {
                List<TransactionImport> transactionImports = importer.addTransactions(fileName);

                for(TransactionImport transactionImport : transactionImports) {
                    TransactionDAO.createTransaction(session, transactionImport.getTimestamp(), transactionImport.getAmount(), getVendorFromRaw(transactionImport.getRawVendor()), account);
                }

                break;
            }
        }
    }

    protected Vendor getVendorFromRaw(String raw) throws BudgetLibraryException {
        return VendorDAO.searchVendorByRawMapping(session, raw);
    }

    public Session getSession() {
        return session;
    }
}
