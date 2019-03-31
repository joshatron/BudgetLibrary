package io.joshatron.budgetlibrary.exception;

public class BudgetLibraryException extends Exception {

    private ErrorCode code;

    public BudgetLibraryException(ErrorCode code) {
        super("The server encountered an error of type: " + code.name());
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
