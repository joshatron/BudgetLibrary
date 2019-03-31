package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;

import java.util.List;

public interface TransactionDAO {

    Transaction createTransaction(Timestamp timestamp, Money amount, Vendor vendor, Account account) throws BudgetLibraryException;
    void updateTransaction(int transactionId, Transaction newTransaction) throws BudgetLibraryException;
    void deleteTransaction(int transactionId) throws BudgetLibraryException;

    List<Transaction> getTransactions(Timestamp start, Timestamp end, Money min, Money max, Vendor vendor, Account account, Type type) throws BudgetLibraryException;
}
