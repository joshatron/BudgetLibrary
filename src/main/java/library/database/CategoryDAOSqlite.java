package library.database;

import library.objects.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDAOSqlite implements CategoryDAO {

    private Connection conn;

    public CategoryDAOSqlite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addCategory(Category category) {
        if (category.isValid()) {
            //category already in database
            if (getCategoryFromName(category.getName()) != null) {
                return;
            }

            String insert = "INSERT INTO categories (name, description, budget) " +
                    "VALUES ( '" + category.getName() + "', '" + category.getDescription() + "', " + category.getBudget() + " );";

            try {
                //add category
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateCategory(Category category, String oldName) {
        if (category.isValid()) {
            //category not in database
            if (getCategoryFromName(category.getName()) == null) {
                return;
            }

            String update = "UPDATE categories " +
                    "SET name = '" + category.getName() + "', description = '" + category.getDescription() + "', budget = " + category.getBudget() + " " +
                    "WHERE name = '" + oldName + "';";

            try {
                //add category
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: Need to add protections to not leave abandoned vendors
    @Override
    public void deleteCategory(String name) {
        if(name != null) {
            String delete = "DELETE FROM categories " +
                    "WHERE name = '" + name + "';";

            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(delete);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();

        String find = "SELECT name, description, budget, id " +
                "FROM categories;";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(find);

            while (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setBudget(rs.getDouble("budget"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category getCategoryFromName(String name) {
        String find = "SELECT name, description, budget, id " +
                "FROM categories " +
                "WHERE name = '" + name + "';";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(find);

            if (rs.next()) {
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setBudget(rs.getDouble("budget"));
                category.setDescription(rs.getString("description"));

                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
