package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;

public interface AccountDAO {

    public void addAccount(Account account);
    public void updateAccount(int accountId, Account newInfo);
    public void deleteAccount(int accountId);

    public Account getAccountById(String accountId);
    public Account getAccountByName(String name);
}
