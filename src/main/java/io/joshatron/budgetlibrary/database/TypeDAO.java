package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;

import java.util.List;

public interface TypeDAO {

    public void addType(Type type);
    public void updateType(int typeId, Type newType);
    public void deleteType(int typeId);

    public List<Type> searchTypes(String name, String description);
    public Type getTypeById(int typeId);
    public Type getTypeByName(String name);
}
