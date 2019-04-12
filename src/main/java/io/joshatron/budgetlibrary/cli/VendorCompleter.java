package io.joshatron.budgetlibrary.cli;

import io.joshatron.budgetlibrary.database.VendorDAO;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class VendorCompleter implements Completer {

    private Session session;

    public VendorCompleter(Session session) {
        this.session = session;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if(parsedLine != null && list != null) {
            List<Vendor> vendors = null;
            try {
                vendors = VendorDAO.getVendors(session, null, null, null);
            }
            catch(BudgetLibraryException e) {
                e.printStackTrace();
            }
            ArrayList<Candidate> candidates = new ArrayList<>();

            for(Vendor vendor : vendors) {
                candidates.add(new Candidate(AttributedString.stripAnsi(vendor.getName()), vendor.getName(), null, null, null, null, true));
            }

            list.addAll(candidates);
        }
    }
}
