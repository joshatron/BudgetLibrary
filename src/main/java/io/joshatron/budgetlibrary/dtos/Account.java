package io.joshatron.budgetlibrary.dtos;

/*
 * Data held by an account
 *
 * Fields include:
 * id: id of the account
 * name: name of account
 * description: description of the account
 */
public class Account {

    private int id;
    private String name;
    private String description;

    public Account() {
        this.id = -1;
        this.name = "";
        this.description = "";
    }

    public Account(String name, String description) {
        this.id = -1;
        this.name = name;
        this.description = description;
    }

    public Account(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public boolean isValid() {
        return id != -1 && !name.isEmpty() && !description.isEmpty();
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
