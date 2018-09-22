package io.joshatron.budgetlibrary;

import io.joshatron.budgetlibrary.database.*;
import io.joshatron.budgetlibrary.imports.ImportDAO;
import io.joshatron.budgetlibrary.imports.ImportDAOCiti;
import io.joshatron.budgetlibrary.objects.Transaction;
import io.joshatron.budgetlibrary.operations.PrintHandler;
import org.jline.builtins.Completers;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseManager.getConnection();
            VendorDAO vendorDAO = new VendorDAOSqlite(conn);
            TransactionDAO transactionDAO = new TransactionDAOSqlite(conn, vendorDAO);
            ImportDAO citi = new ImportDAOCiti(transactionDAO, vendorDAO);
            LineReader commandReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("import", "print", "exit"))
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
                    String file = fileReader.readLine("What is the file name? ").trim();
                    System.out.println("starting citi import");
                    citi.addTransactions(file);
                    System.out.println("ending citi import");

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
