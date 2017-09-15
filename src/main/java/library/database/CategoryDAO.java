package library.database;

import library.objects.Category;

import java.util.ArrayList;

public interface CategoryDAO {

    public void addCategory(Category category);
    public void updateCategory(Category category, String oldName);
    public void deleteCategory(String name);

    public ArrayList<Category> getAllCategories();
    public Category getCategoryFromName(String name);
}
