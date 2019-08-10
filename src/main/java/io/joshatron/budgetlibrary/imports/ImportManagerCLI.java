package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.cli.TypeCompleter;
import io.joshatron.budgetlibrary.cli.VendorCompleter;
import io.joshatron.budgetlibrary.database.TypeDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Type;
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
        Vendor vendor = VendorDAO.searchVendorByRawMapping(getSession(), raw);

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

            LineReader vendorReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new VendorCompleter(getSession()))
                    .build();

            while(true) {
                String vendorName = vendorReader.readLine("What is the vendor for this transaction? ").trim();

                Vendor foundVendor = VendorDAO.getVendorByName(getSession(), vendorName);
                if(foundVendor != null) {
                    return foundVendor;
                }

                String answer = yesNoReader.readLine("A vendor by that name could not be found. Would you like to create one named '" + vendorName + "'?");
                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    return VendorDAO.createVendor(getSession(), vendorName, getType());
                }
            }
        }
        catch(IOException e) {
            throw new BudgetLibraryException(ErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }

    private Type getType() throws BudgetLibraryException {
        try {
            LineReader plainReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .build();

            LineReader yesNoReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("yes", "no"))
                    .build();

            LineReader typeReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new TypeCompleter(getSession()))
                    .build();

            while(true) {
                String typeName = typeReader.readLine("What is the type for this vendor? ").trim();

                Type foundType = TypeDAO.getTypeByName(getSession(), typeName);
                if(foundType != null) {
                    return foundType;
                }

                String answer = yesNoReader.readLine("A type by that name could not be found. Would you like to create one named '" + typeName + "'?");
                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    String description = plainReader.readLine("What is the description of this type? ").trim();
                    return TypeDAO.createType(getSession(), typeName, description);
                }
            }
        }
        catch(IOException e) {
            throw new BudgetLibraryException(ErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }
}
