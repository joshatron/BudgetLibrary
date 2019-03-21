package io.joshatron.budgetlibrary.dtos;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VendorTest {

    private Vendor createVendor() {
        Vendor vendor = new Vendor("Safeway", "grocery");

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
        String type = "grocery";
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setType(type);
        Assert.assertEquals(vendor.getName(), name);
        Assert.assertEquals(vendor.getType(), type);
    }
}
