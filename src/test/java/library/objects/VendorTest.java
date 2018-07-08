package library.objects;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VendorTest {

    private Vendor createVendor() {
        ArrayList<String> rawNames = new ArrayList<String>();
        rawNames.add("Safeway");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor("Safeway", rawNames, tags);

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
        ArrayList<String> rawNames = new ArrayList<String>();
        rawNames.add("Safeway");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("groceries");
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendor.setRawNames(rawNames);
        vendor.setTags(tags);
        Assert.assertEquals(vendor.getName(), name);
        Assert.assertEquals(vendor.getRawNames().size(), rawNames.size());
        Assert.assertEquals(vendor.getRawNames().get(0), rawNames.get(0));
        Assert.assertEquals(vendor.getTags().size(), tags.size());
        Assert.assertEquals(vendor.getTags().get(0), tags.get(0));
    }
}