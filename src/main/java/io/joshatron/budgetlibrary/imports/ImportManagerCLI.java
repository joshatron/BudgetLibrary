package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.cli.CliGetter;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ImportManagerCLI extends ImportManager {

    public ImportManagerCLI(Session session) {
        super(session);
    }

    @Override
    protected Vendor getVendorFromRaw(String raw) throws BudgetLibraryException {
        Vendor vendor = null;
        try {
            vendor = VendorDAO.searchVendorByRawMapping(getSession(), raw);
        }
        catch(BudgetLibraryException e) {
            if(e.getCode() != ErrorCode.NO_RESULT_FOUND) {
                throw e;
            }
        }

        try {
            LineReader yesNoReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("yes", "no"))
                    .build();

            if(vendor != null) {
                String answer = yesNoReader.readLine("Is this the correct vendor \'" + vendor.getName() + "\'? ").trim();

                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    return vendor;
                }
            }

            return CliGetter.getVendor(getSession());
        }
        catch(IOException e) {
            throw new BudgetLibraryException(ErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }
}
