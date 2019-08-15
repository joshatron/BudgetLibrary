package io.joshatron.budgetlibrary.exception;

public class BudgetLibraryException extends Exception {

    private BudgetLibraryErrorCode code;

    public BudgetLibraryException(BudgetLibraryErrorCode code) {
        super("The server encountered an error of type: " + code.name());
        this.code = code;
    }

    public BudgetLibraryErrorCode getCode() {
        return code;
    }
}
