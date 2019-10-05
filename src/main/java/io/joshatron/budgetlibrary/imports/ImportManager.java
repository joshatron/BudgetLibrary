package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;

import java.time.format.DateTimeFormatter;
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
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    System.out.println("Processing transaction: " + formatter.format(transactionImport.getTimestamp()) +
                            ", " + transactionImport.getAmount().toString() + ", " + transactionImport.getRawVendor());

                    Vendor vendor = getVendorFromRaw(transactionImport.getRawVendor());
                    if(!vendor.hasRawMapping(transactionImport.getRawVendor())) {
                        VendorDAO.createVendorRawMapping(session, vendor.getId(), transactionImport.getRawVendor());
                    }

                    List<Transaction> transactions = TransactionDAO.searchTransactions(session, transactionImport.getTimestamp(),
                            transactionImport.getTimestamp(), transactionImport.getAmount(), transactionImport.getAmount(),
                            vendor, account, null);
                    if(!transactions.isEmpty()) {
                        System.out.println("Duplicate transaction found, skipping");
                    }
                    else {
                        TransactionDAO.createTransaction(session, transactionImport.getTimestamp(), transactionImport.getAmount(), vendor, account);
                    }
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
