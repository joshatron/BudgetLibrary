package io.joshatron.budgetlibrary.objects;

import java.util.ArrayList;

/*
 * Data held by vendor
 *
 * Fields include:
 * name: name of vendor
 * tags: tags relating to this vendor
 */
public class Vendor {
    private String name;
    private ArrayList<String> tags;

    public Vendor() {
        this.name = null;
        this.tags = new ArrayList<String>();
    }

    public Vendor(String name, ArrayList<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public boolean isValid() {
        if(name == null || name.equals("")) {
            return false;
        }
        return true;
    }

    public void addtag(String tag) {
        this.tags.add(tag);
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
