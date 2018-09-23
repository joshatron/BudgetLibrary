package io.joshatron.budgetlibrary;

import io.joshatron.budgetlibrary.database.*;
import io.joshatron.budgetlibrary.imports.ImportDAO;
import io.joshatron.budgetlibrary.imports.ImportDAOAlliant;
import io.joshatron.budgetlibrary.imports.ImportDAOCiti;
import io.joshatron.budgetlibrary.operations.PrintHandler;
import org.jline.builtins.Completers;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();
            VendorDAO vendorDAO = new VendorDAOSqlite(conn);
            TransactionDAO transactionDAO = new TransactionDAOSqlite(conn, vendorDAO);
            ImportDAO citi = new ImportDAOCiti(transactionDAO, vendorDAO);
            ImportDAO alliant = new ImportDAOAlliant(transactionDAO, vendorDAO);
            LineReader commandReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("import", "print", "exit"))
                    .build();
            LineReader importReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("citi", "alliant"))
                    .build();
            LineReader fileReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new Completers.FilesCompleter(new File("")))
                    .build();

            System.out.println("------------------");
            System.out.println("| Budget Manager |");
            System.out.println("------------------");
            System.out.println();

            while (true) {
                String input = commandReader.readLine("> ").trim().toLowerCase();

                if(input.equals("import")) {
                    String account = importReader.readLine("Which bank account is being imported? ").trim();
                    String file = fileReader.readLine("What is the file name? ").trim();
                    if(account.toLowerCase().equals("citi")) {
                        System.out.println("starting citi import");
                        citi.addTransactions(file);
                        System.out.println("ending citi import");
                    }
                    else if(account.toLowerCase().equals("alliant")) {
                        System.out.println("starting alliant import");
                        alliant.addTransactions(file);
                        System.out.println("ending alliant import");
                    }

                }
                else if(input.equals("print")) {
                    PrintHandler.printTransactions(transactionDAO.getAllTransactions());
                }
                else if(input.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
