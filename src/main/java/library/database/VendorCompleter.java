package library.database;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class VendorCompleter implements Completer {

    private VendorDAO vendorDAO;

    public VendorCompleter(VendorDAO vendorDAO) {
        this.vendorDAO = vendorDAO;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if(parsedLine != null && list != null) {
            ArrayList<String> vendors = vendorDAO.getVendorsFromRaw();
            ArrayList<Candidate> candidates = new ArrayList<>();

            for(String vendor : vendors) {
                candidates.add(new Candidate(AttributedString.stripAnsi(vendor), vendor, null, null, null, null, true));
            }

            list.addAll(candidates);
        }
    }
}
