package library;

import library.database.*;
import library.imports.ImportDAO;
import library.imports.ImportDAOCiti;
import library.objects.Transaction;
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
                    .completer(new StringsCompleter("import", "exit"))
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
                    ArrayList<Transaction> transactions = citi.getTransactions(file);
                    System.out.println("ending citi import");
                    transactionDAO.addTransactions(transactions);

                    ArrayList<Transaction> transactions1 = transactionDAO.getAllTransactions();

                    for (Transaction transaction : transactions1) {
                        System.out.println(transaction);
                    }
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
