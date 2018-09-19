package io.joshatron.budgetlibrary.database;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class TypeCompleter implements Completer {

    private VendorDAO vendorDAO;

    public TypeCompleter(VendorDAO vendorDAO) {
        this.vendorDAO = vendorDAO;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if(parsedLine != null && list != null) {
            String[] vendors = vendorDAO.getVendorTypes();
            ArrayList<Candidate> candidates = new ArrayList<>();

            for(String vendor : vendors) {
                candidates.add(new Candidate(AttributedString.stripAnsi(vendor), vendor, null, null, null, null, true));
            }

            list.addAll(candidates);
        }
    }
}
