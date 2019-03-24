package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;

import java.util.List;

public interface AccountDAO {

    public void addAccount(Account account);
    public void updateAccount(int accountId, Account newInfo);
    public void deleteAccount(int accountId);

    public List<Account> searchAccounts(String name, String description);
    public Account getAccountById(String accountId);
    public Account getAccountByName(String name);
}
