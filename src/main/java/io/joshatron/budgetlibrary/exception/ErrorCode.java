package io.joshatron.budgetlibrary.exception;

public enum ErrorCode {
    TRANSACTION_EXISTS,
    INVALID_TRANSACTION,
    VENDOR_EXISTS,
    INVALID_VENDOR,
    INVALID_VENDOR_MAPPING,
    VENDOR_HAS_DEPENDANT_TRANSACTIONS,
    DATABASE_ERROR
}
