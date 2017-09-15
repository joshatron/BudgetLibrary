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
    private double budget;

    public Category() {
        this.name = null;
        this.description = null;
        this.budget = 0;
    }

    public Category(String name, String description, double budget) {
        this.name = name;
        this.description = description;
        this.budget = budget;
    }

    public boolean isValid() {
        if(name == null) {
            return false;
        }
        if(budget < -0.0001) {
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
