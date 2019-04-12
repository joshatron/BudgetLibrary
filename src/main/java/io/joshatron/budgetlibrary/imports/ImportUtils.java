package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.cli.TypeCompleter;
import io.joshatron.budgetlibrary.cli.VendorCompleter;
import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Vendor;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ImportUtils {

    public static Vendor getVendorFromRaw(String raw, VendorDAO vendorDAO) {
        Vendor vendor = vendorDAO.getVendorFromRaw(raw);

        if(vendor == null) {
            try {
                Terminal terminal = TerminalBuilder.terminal();
                LineReader lineReader = LineReaderBuilder.builder()
                        .terminal(terminal)
                        .completer(new VendorCompleter(vendorDAO))
                        .build();
                LineReader typeReader = LineReaderBuilder.builder()
                        .terminal(terminal)
                        .completer(new TypeCompleter(vendorDAO))
                        .build();

                String vendorName = lineReader.readLine("What is the vendor for " + raw + "? ");
                vendorName = vendorName.trim();
                vendorDAO.addVendorRawMapping(vendorName, raw);
                vendor = vendorDAO.getVendorFromName(vendorName);

                if (vendor == null) {
                    String vendorType = typeReader.readLine("What is the type for " + vendorName + "? ");
                    vendorType = vendorType.trim();
                    vendor = new Vendor(vendorName, vendorType);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return vendor;
    }

    public static List<String> getLines(String file) {
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(file));
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
