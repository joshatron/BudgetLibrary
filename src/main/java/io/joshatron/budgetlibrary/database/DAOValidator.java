package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.*;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import io.joshatron.budgetlibrary.exception.ErrorCode;
import org.hibernate.Session;

import java.util.List;

public class DAOValidator {
    public DAOValidator() throws BudgetLibraryException {
        throw new BudgetLibraryException(ErrorCode.INVALID_INITIALIZATION);
    }

    public static void validateSession(Session session) throws BudgetLibraryException {
        if(session == null || !session.isOpen()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_SESSION);
        }
    }

    public static void validateString(String string) throws BudgetLibraryException {
        if(string == null || string.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_STRING);
        }
    }

    public static void validateAccount(Account account) throws BudgetLibraryException {
        if(account == null || !account.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_ACCOUNT);
        }
    }

    public static void validateType(Type type) throws BudgetLibraryException {
        if(type == null || !type.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TYPE);
        }
    }

    public static void validateVendor(Vendor vendor) throws BudgetLibraryException {
        if(vendor == null || !vendor.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_VENDOR);
        }
    }

    public static void validateTimestamp(Timestamp timestamp) throws BudgetLibraryException {
        if(timestamp == null) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TIMESTAMP);
        }
    }

    public static void validateMoney(Money money) throws BudgetLibraryException {
        if(money == null) {
            throw new BudgetLibraryException(ErrorCode.INVALID_MONEY);
        }
    }

    public static void validateTransaction(Transaction transaction) throws BudgetLibraryException {
        if(transaction == null || !transaction.isValid()) {
            throw new BudgetLibraryException(ErrorCode.INVALID_TRANSACTION);
        }
    }

    public static void validateOnlyOneResult(List results) throws BudgetLibraryException {
        if(results.isEmpty()) {
            throw new BudgetLibraryException(ErrorCode.NO_RESULT_FOUND);
        }
        else if(results.size() > 1){
            throw new BudgetLibraryException(ErrorCode.TOO_MANY_RESULTS_FOUND);
        }
    }

    public static void validateStringInList(String str, List<String> list) throws BudgetLibraryException {
        for(String entry : list) {
            if(str.equalsIgnoreCase(entry)) {
                return;
            }
        }

        throw new BudgetLibraryException(ErrorCode.STRING_NOT_IN_LIST);
    }

    public static void validateStringNotInList(String str, List<String> list) throws BudgetLibraryException {
        for(String entry : list) {
            if(str.equalsIgnoreCase(entry)) {
                throw new BudgetLibraryException(ErrorCode.STRING_ALREADY_IN_LIST);
            }
        }
    }
}
