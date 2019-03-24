package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;

public interface TypeDAO {

    public void addType(Type type);
    public void updateType(int typeId, Type newType);
    public void deleteType(int typeId);

    public Type getTypeById(int typeId);
    public Type getTypeByName(String name);
}
