package io.joshatron.budgetlibrary.cli;

import io.joshatron.budgetlibrary.database.AccountDAO;
import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.List;

public class AccountCompleter implements Completer {

    private Session session;

    public AccountCompleter(Session session) {
        this.session = session;
    }

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if(parsedLine != null && list != null) {
            List<Account> accounts = null;
            try {
                accounts = AccountDAO.getAllAccounts(session);
            }
            catch(BudgetLibraryException e) {
                e.printStackTrace();
            }
            ArrayList<Candidate> candidates = new ArrayList<>();

            for(Account account : accounts) {
                candidates.add(new Candidate(AttributedString.stripAnsi(account.getName()), account.getName(), null, null, null, null, true));
            }

            list.addAll(candidates);
        }
    }
}
