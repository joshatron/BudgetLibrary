package library.objects;

/*
 * Data held by category
 *
 * Fields include:
 * name: name of category
 * description: description about category
 * budget: allotted budget of category
 */
public class Category {
    private String name;
    private String description;
    private int budget;

    public Category() {
        this.name = null;
        this.description = null;
        this.budget = 0;
    }

    public Category(String name, String description, int budget) {
        this.name = name;
        this.description = description;
        this.budget = budget;
    }

    public boolean isValid() {
        if(name == null || name.equals("")) {
            return false;
        }
        if(budget <= 0) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
