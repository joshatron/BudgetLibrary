package library.objects;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;

public class VendorTest {

    private Vendor createVendor() {
        Category category = new Category("Food", "food", 100000);

        ArrayList<String> rawNames = new ArrayList<String>();
        rawNames.add("Safeway");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor("Safeway", rawNames, tags, category);

        return vendor;
    }

    @Test
    public void testIsValidTrue() throws Exception {
        Vendor vendor = createVendor();
        Assert.assertEquals(vendor.isValid(), true);
    }

    @Test
    public void testIsValidNullName() throws Exception {
        Vendor vendor = createVendor();
        vendor.setName(null);
        Assert.assertEquals(vendor.isValid(), false);
    }

    @Test
    public void testIsValidEmptyName() throws Exception {
        Vendor vendor = createVendor();
        vendor.setName("");
        Assert.assertEquals(vendor.isValid(), false);
    }

    @Test
    public void testIsValidNullCategory() throws Exception {
        Vendor vendor = createVendor();
        vendor.setCategory(null);
        Assert.assertEquals(vendor.isValid(), false);
    }

    @Test
    public void testIsValidInvalidCategory() throws Exception {
        Vendor vendor = createVendor();
        vendor.setCategory(new Category());
        Assert.assertEquals(vendor.isValid(), false);
    }

    @Test
    public void testSettersGetters() throws Exception {
        String name = "Safeway";
        ArrayList<String> rawNames = new ArrayList<String>();
        rawNames.add("Safeway");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Category category = new Category("Food", "food", 100000);
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setRawNames(rawNames);
        vendor.setTags(tags);
        vendor.setCategory(category);
        Assert.assertEquals(vendor.getName(), name);
        Assert.assertEquals(vendor.getRawNames().size(), rawNames.size());
        Assert.assertEquals(vendor.getRawNames().get(0), rawNames.get(0));
        Assert.assertEquals(vendor.getTags().size(), tags.size());
        Assert.assertEquals(vendor.getTags().get(0), tags.get(0));
        Assert.assertEquals(vendor.getCategory().getName(), category.getName());
    }
}