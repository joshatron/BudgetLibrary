package io.joshatron.budgetlibrary.objects;

import java.util.ArrayList;

/*
 * Data held by vendor
 *
 * Fields include:
 * name: name of vendor
 * type: type of product the vendor sells
 */
public class Vendor {
    private String name;
    private String type;

    public Vendor() {
        this.name = null;
        this.type = null;
    }

    public Vendor(String name, String type) {
        this.name = name;
        this.type = type
    }

    public boolean isValid() {
        if(name == null || name.equals("") || type == null || type.equals("")) {
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

    private String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }
}
