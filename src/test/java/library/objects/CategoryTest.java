package library.objects;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoryTest {

    private Category createCategory() {
        Category category = new Category("Food", "food", 100000);
        return category;
    }

    @Test
    public void testIsValidTrue() throws Exception {
        Category category = createCategory();
        Assert.assertEquals(category.isValid(), true);
    }

    @Test
    public void testIsValidNullName() throws Exception {
        Category category = createCategory();
        category.setName(null);
        Assert.assertEquals(category.isValid(), false);
    }

    @Test
    public void testIsValidEmptyName() throws Exception {
        Category category = createCategory();
        category.setName("");
        Assert.assertEquals(category.isValid(), false);
    }

    @Test
    public void testIsValidZeroBudget() throws Exception {
        Category category = createCategory();
        category.setBudget(0);
        Assert.assertEquals(category.isValid(), false);
    }

    @Test
    public void testIsValidNegativeBudget() throws Exception {
        Category category = createCategory();
        category.setBudget(-1000);
        Assert.assertEquals(category.isValid(), false);
    }

    @Test
    public void testSettersGetters() throws Exception {
        String name = "Food";
        String description = "food";
        int budget = 100000;
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setBudget(budget);
        Assert.assertEquals(category.getName(), name);
        Assert.assertEquals(category.getDescription(), description);
        Assert.assertEquals(category.getBudget(), budget);
    }
}