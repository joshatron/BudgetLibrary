package io.joshatron.budgetlibrary.dtos;

/*
 * Data held by a vendor
 *
 * Fields include:
 * name: name of vendor
 * type: type of product the vendor sells
 */
public class Vendor {
    private String name;
    private Type type;

    public Vendor() {
        this.name = null;
        this.type = null;
    }

    public Vendor(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public boolean isValid() {
        if(name == null || name.equals("") || type == null || type.equals("")) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
