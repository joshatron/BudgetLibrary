package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;

import java.util.List;

public interface TypeDAO {

    Type createType(String name, String description) throws BudgetLibraryException;
    void updateType(int typeId, Type newType) throws BudgetLibraryException;
    void deleteType(int typeId) throws BudgetLibraryException;

    List<Type> getTypes(String name, String description) throws BudgetLibraryException;
}
