package io.joshatron.budgetlibrary.dtos;

/*
 * Data held by a type
 *
 * Fields include:
 * id: id of the type
 * name: name of type
 * description: description of the type
 */
public class Type {

    private int id;
    private String name;
    private String description;

    public Type() {
        this.id = -1;
        this.name = "";
        this.description = "";
    }

    public Type(String name, String description) {
        this.id = -1;
        this.name = name;
        this.description = description;
    }

    public Type(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public boolean isValid() {
        return id != -1 && name != null && !name.isEmpty() && description != null && !description.isEmpty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
