package io.joshatron.budgetlibrary.cli;

import io.joshatron.budgetlibrary.database.TypeDAO;
import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class TypeCompleter implements Completer {

    private Session session;

    public TypeCompleter(Session session) {
        this.session = session;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if(parsedLine != null && list != null) {
            List<Type> types = null;
            try {
                types = TypeDAO.getAllTypes(session);
            }
            catch(BudgetLibraryException e) {
                e.printStackTrace();
            }
            ArrayList<Candidate> candidates = new ArrayList<>();

            for(Type type : types) {
                candidates.add(new Candidate(AttributedString.stripAnsi(type.getName()), type.getName(), null, null, null, null, true));
            }

            list.addAll(candidates);
        }
    }
}
