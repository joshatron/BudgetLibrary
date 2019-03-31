package io.joshatron.budgetlibrary.dtos;

/*
 * Data held by a vendor
 *
 * Fields include:
 * id: id of the vendor
 * name: name of vendor
 * type: type of product the vendor sells
 */
public class Vendor {

    private int id;
    private String name;
    private Type type;

    public Vendor() {
        this.id = -1;
        this.name = "";
        this.type = null;
    }

    public Vendor(String name, Type type) {
        this.id = -1;
        this.name = name;
        this.type = type;
    }

    public Vendor(int id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public boolean isValid() {
        return id != -1 && !name.isEmpty() && type != null;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
