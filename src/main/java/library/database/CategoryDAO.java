package library.database;

import library.objects.Category;

import java.util.ArrayList;

public interface CategoryDAO {

    void addCategory(Category category);
    void updateCategory(Category category, String oldName);
    void deleteCategory(String name);

    ArrayList<Category> getAllCategories();
    Category getCategoryFromName(String name);
}
