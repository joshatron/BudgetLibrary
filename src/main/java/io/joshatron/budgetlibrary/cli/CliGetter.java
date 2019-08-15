package io.joshatron.budgetlibrary.cli;

import io.joshatron.budgetlibrary.database.AccountDAO;
import io.joshatron.budgetlibrary.database.TypeDAO;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.BudgetLibraryErrorCode;
import org.hibernate.Session;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class CliGetter {

    public static Vendor getVendor(Session session) throws BudgetLibraryException {
        try {
            LineReader yesNoReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("yes", "no"))
                    .build();

            LineReader vendorReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new VendorCompleter(session))
                    .build();

            while(true) {
                String vendorName = vendorReader.readLine("What is the vendor for this transaction? ").trim();

                try {
                    return VendorDAO.getVendorByName(session, vendorName);
                }
                catch(BudgetLibraryException e) {
                    if(e.getCode() != BudgetLibraryErrorCode.NO_RESULT_FOUND) {
                        throw e;
                    }
                }

                String answer = yesNoReader.readLine("A vendor by that name could not be found. Would you like to create one named '" + vendorName + "'? ");
                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    return VendorDAO.createVendor(session, vendorName, getType(session));
                }
            }
        }
        catch(IOException e) {
            throw new BudgetLibraryException(BudgetLibraryErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }

    public static Type getType(Session session) throws BudgetLibraryException {
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
                    .completer(new TypeCompleter(session))
                    .build();

            while(true) {
                String typeName = typeReader.readLine("What is the name of this type? ").trim();

                try {
                    return TypeDAO.getTypeByName(session, typeName);
                }
                catch(BudgetLibraryException e) {
                    if(e.getCode() != BudgetLibraryErrorCode.NO_RESULT_FOUND) {
                        throw e;
                    }
                }

                String answer = yesNoReader.readLine("A type by that name could not be found. Would you like to create one named '" + typeName + "'? ");
                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    String description = plainReader.readLine("What is the description of this type? ").trim();
                    return TypeDAO.createType(session, typeName, description);
                }
            }
        }
        catch(IOException e) {
            throw new BudgetLibraryException(BudgetLibraryErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }

    public static Account getAccount(Session session) throws BudgetLibraryException {
        try {
            LineReader plainReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .build();

            LineReader yesNoReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("yes", "no"))
                    .build();

            LineReader accountReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new AccountCompleter(session))
                    .build();

            while(true) {
                String accountName = accountReader.readLine("What is the name of the account? ").trim();

                try {
                    return AccountDAO.getAccountByName(session, accountName);
                }
                catch(BudgetLibraryException e) {
                    if(e.getCode() != BudgetLibraryErrorCode.NO_RESULT_FOUND) {
                        throw e;
                    }
                }

                String answer = yesNoReader.readLine("An account by that name could not be found. Would you like to create one named '" + accountName + "'? ");
                if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    String bank = plainReader.readLine("What is the bank of this account? ").trim();
                    String description = plainReader.readLine("What is the description of this account? ").trim();
                    return AccountDAO.createAccount(session, accountName, bank, description);
                }
            }
        }
        catch(IOException e) {
            throw new BudgetLibraryException(BudgetLibraryErrorCode.COULD_NOT_CONNECT_TO_TERMINAL);
        }
    }
}
