package io.joshatron.budgetlibrary.objects;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VendorTest {

    private Vendor createVendor() {
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor("Safeway", tags);

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
    public void testSettersGetters() throws Exception {
        String name = "Safeway";
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setTags(tags);
        Assert.assertEquals(vendor.getName(), name);
        Assert.assertEquals(vendor.getTags().size(), tags.size());
        Assert.assertEquals(vendor.getTags().get(0), tags.get(0));
    }
}
