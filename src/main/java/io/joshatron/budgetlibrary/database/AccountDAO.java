package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Account;

import java.util.List;

public interface AccountDAO {

    int addAccount(Account account);
    void updateAccount(int accountId, Account newInfo);
    void deleteAccount(int accountId);

    List<Account> searchAccounts(String name, String description);
    Account getAccountById(String accountId);
    Account getAccountByName(String name);
}
