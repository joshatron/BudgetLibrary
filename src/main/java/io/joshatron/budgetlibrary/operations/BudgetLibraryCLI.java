package io.joshatron.budgetlibrary.operations;

import io.joshatron.budgetlibrary.cli.AccountCompleter;
import io.joshatron.budgetlibrary.cli.TypeCompleter;
import io.joshatron.budgetlibrary.cli.VendorCompleter;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public class BudgetLibraryCLI extends BudgetLibrary {
    @Override
    protected Vendor getVendorFromRaw(String raw) throws BudgetLibraryException {
        List<Vendor> vendors = VendorDAO.getVendors(getSession(), null, null, raw);

        if(vendors == null || vendors.isEmpty()) {
            try {
                LineReader reader = LineReaderBuilder.builder()
                        .terminal(TerminalBuilder.terminal())
                        .completer(new VendorCompleter(getSession()))
                        .build();

                String vendorName = reader.readLine("What is the vendor for this transaction? ").trim();

                List<Vendor> foundVendors = VendorDAO.getVendors(getSession(), vendorName, null, null);
                if(foundVendors != null && !foundVendors.isEmpty()) {
                    return foundVendors.get(0);
                }
                else {
                    //TODO: handle new vendor
                }
            }
            catch(IOException e) {
                throw new BudgetLibraryException(ErrorCode.IO_ERROR);
            }
        }

        return vendors.get(0);
    }

    public AccountCompleter getAccountCompleter() {
        return new AccountCompleter(getSession());
    }

    public TypeCompleter getTypeCompleter() {
        return new TypeCompleter(getSession());
    }

    public VendorCompleter getVendorCompleter() {
        return new VendorCompleter(getSession());
    }
}
