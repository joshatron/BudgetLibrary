package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.dtos.Vendor;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class VendorDAOTest {
    Session session;

    @BeforeTest
    public void initialize() {
        session = DAOUtils.createSession();
    }

    @AfterMethod
    public void deleteAfter() {
        DAOUtils.cleanUp(session);
    }

    @AfterTest
    public void closeSession() {
        session.close();
    }

    @Test
    public void createVendorBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            Assert.assertEquals(vendor.getName(), "vons");
            Assert.assertEquals(vendor.getType(), type);

            List<Vendor> vendors = VendorDAO.getVendors(session, null, null, null);
            Assert.assertEquals(vendors.size(), 1);
            Assert.assertEquals(vendors.get(0), vendor);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void updateVendorBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Type type2 = TypeDAO.createType(session, "gas", "oil");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            VendorDAO.updateVendor(session, vendor.getId(), "shell", type2);

            List<Vendor> vendors = VendorDAO.getVendors(session, null, null, null);
            Assert.assertEquals(vendors.size(), 1);
            Assert.assertEquals(vendors.get(0).getName(), "shell");
            Assert.assertEquals(vendors.get(0).getType(), type2);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void deleteVendorBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            VendorDAO.deleteVendor(session, vendor.getId());

            List<Vendor> vendors = VendorDAO.getVendors(session, null, null, null);
            Assert.assertEquals(vendors.size(), 0);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getVendorsByName() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "safeway", type);

            List<Vendor> vendors = VendorDAO.getVendors(session, "vons", null, null);
            Assert.assertEquals(vendors.size(), 1);
            vendors = VendorDAO.getVendors(session, "trader joes", null, null);
            Assert.assertEquals(vendors.size(), 1);
            vendors = VendorDAO.getVendors(session, "safeway", null, null);
            Assert.assertEquals(vendors.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getVendorsByType() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Type type2 = TypeDAO.createType(session, "gas", "oil");
            Type type3 = TypeDAO.createType(session, "theatre", "entertainment");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "shell", type2);
            VendorDAO.createVendor(session, "drive ins", type3);

            List<Vendor> vendors = VendorDAO.getVendors(session, null, type, null);
            Assert.assertEquals(vendors.size(), 2);
            vendors = VendorDAO.getVendors(session, null, type2, null);
            Assert.assertEquals(vendors.size(), 1);
            vendors = VendorDAO.getVendors(session, null, type3, null);
            Assert.assertEquals(vendors.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
