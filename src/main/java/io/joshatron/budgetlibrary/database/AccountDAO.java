package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;

import java.util.List;

public interface AccountDAO {

    Account createAccount(String name, String description) throws BudgetLibraryException;
    void updateAccount(int accountId, Account newInfo) throws BudgetLibraryException;
    void deleteAccount(int accountId) throws BudgetLibraryException;

    List<Account> getAccounts(String name, String description);
}
