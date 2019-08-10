package io.joshatron.budgetlibrary;

import io.joshatron.budgetlibrary.cli.CliGetter;
import io.joshatron.budgetlibrary.database.TransactionDAO;
import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.imports.ImportManagerCLI;
import io.joshatron.budgetlibrary.operations.PrintHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jline.builtins.Completers;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.TerminalBuilder;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            Session session = factory.openSession();

            ImportManagerCLI importManager = new ImportManagerCLI(session);

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
                    Account account = CliGetter.getAccount(session);
                    String file = fileReader.readLine("What is the file name? ").trim();
                    importManager.importTransactions(file, account);
                }
                else if(input.equals("print")) {
                    PrintHandler.printTransactions(TransactionDAO.getAllTransactions(session));
                }
                else if(input.equals("exit")) {
                    break;
                }
            }
        } catch (IOException | BudgetLibraryException e) {
            e.printStackTrace();
        }
    }
}
