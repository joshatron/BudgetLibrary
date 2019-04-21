package io.joshatron.budgetlibrary;

import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.operations.BudgetLibrary;
import io.joshatron.budgetlibrary.operations.BudgetLibraryCLI;
import io.joshatron.budgetlibrary.operations.PrintHandler;
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
            BudgetLibrary budgetLibrary = new BudgetLibraryCLI();

            LineReader importReader = LineReaderBuilder.builder()
                    .terminal(TerminalBuilder.terminal())
                    .completer(new StringsCompleter("alliant", "citi"))
                    .build();
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
                    String account = importReader.readLine("Which bank account is being imported? ").trim();
                    String file = fileReader.readLine("What is the file name? ").trim();
                    budgetLibrary.importTransactions(file, account, account);
                }
                else if(input.equals("print")) {
                    PrintHandler.printTransactions(budgetLibrary.getTransactions(null, null, null, null, null, null, null));
                }
                else if(input.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(BudgetLibraryException e) {
            e.printStackTrace();
        }
    }
}
