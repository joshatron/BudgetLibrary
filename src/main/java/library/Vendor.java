package library;

import java.util.ArrayList;

/*
 * Data held by vendor
 *
 * Fields include:
 * name: name of vendor
 * rawNames: all names that refer to this vendor
 * tags: tags relating to this vendor
 * category: category of this vendor
 */
public class Vendor {
    private String name;
    private ArrayList<String> rawNames;
    private ArrayList<String> tags;
    private Category category;

    public Vendor() {
        this.name = null;
        this.rawNames = new ArrayList<String>();
        this.tags = new ArrayList<String>();
        this.category = null;
    }

    public Vendor(String name, ArrayList<String> rawNames, ArrayList<String> tags, Category category) {
        this.name = name;
        this.rawNames = rawNames;
        this.tags = tags;
        this.category = category;
    }

    public boolean isValid() {
        if(name == null) {
            System.out.println("Vendor name can't be null");
            return false;
        }
        if(category == null || !category.isValid()) {
            System.out.println("Category can't be invalid");
            return false;
        }
        return true;
    }

    public void addRawName(String rawName) {
        this.rawNames.add(rawName);
    }

    public void addtag(String tag) {
        this.tags.add(tag);
    }

    public void removeRawName(String rawName) {
        this.rawNames.remove(rawName);
    }

    public void removetag(String tag) {
        this.tags.remove(tag);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRawNames() {
        return rawNames;
    }

    public void setRawNames(ArrayList<String> rawNames) {
        this.rawNames = rawNames;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
