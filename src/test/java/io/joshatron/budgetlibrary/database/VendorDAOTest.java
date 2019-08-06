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

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
            Assert.assertEquals(vendors.size(), 1);
            Assert.assertEquals(vendors.get(0), vendor);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void createVendorRawMappingBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendorRawMapping(session, vendor.getId(), "VONS #81");

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
            Assert.assertEquals(vendors.get(0).getRawMappings().size(), 1);
            Assert.assertEquals(vendors.get(0).getRawMappings().get(0), "VONS #81");
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

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
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

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
            Assert.assertEquals(vendors.size(), 0);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void deleteVendorRawMappingBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Vendor vendor = VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendorRawMapping(session, vendor.getId(), "VONS #81");
            VendorDAO.createVendorRawMapping(session, vendor.getId(), "VONS #64");
            VendorDAO.deleteVendorRawMapping(session, vendor.getId(), "VONS #81");

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
            Assert.assertEquals(vendors.get(0).getRawMappings().size(), 1);
            Assert.assertEquals(vendors.get(0).getRawMappings().get(0), "VONS #64");
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getAllVendorBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "safeway", type);

            List<Vendor> vendors = VendorDAO.getAllVendors(session);
            Assert.assertEquals(vendors.size(), 3);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getVendorByNameBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "safeway", type);

            Vendor vendor = VendorDAO.getVendorByName(session, "vons");
            Assert.assertEquals(vendor.getName(), "vons");
            Assert.assertEquals(vendor.getType(), type);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getVendorsByTypeBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Type type2 = TypeDAO.createType(session, "gas", "oil");
            Type type3 = TypeDAO.createType(session, "theatre", "entertainment");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "shell", type2);
            VendorDAO.createVendor(session, "drive ins", type3);

            List<Vendor> vendors = VendorDAO.getVendorsByType(session, type);
            Assert.assertEquals(vendors.size(), 2);
            vendors = VendorDAO.getVendorsByType(session, type2);
            Assert.assertEquals(vendors.size(), 1);
            vendors = VendorDAO.getVendorsByType(session, type3);
            Assert.assertEquals(vendors.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getVendorByRawMappingBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Vendor vons = VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendorRawMapping(session, vons.getId(), "VONS #81");
            VendorDAO.createVendorRawMapping(session, vons.getId(), "VONS #64");
            Vendor joes = VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendorRawMapping(session, joes.getId(), "JOE's, TRADER");
            Vendor safeway = VendorDAO.createVendor(session, "safeway", type);
            VendorDAO.createVendorRawMapping(session, safeway.getId(), "#86");

            Vendor vendor = VendorDAO.getVendorByRawMapping(session, "VONS #64");
            Assert.assertEquals(vendor, vons);
            vendor = VendorDAO.getVendorByRawMapping(session, "#86");
            Assert.assertEquals(vendor, safeway);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void searchVendorsByNameBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            VendorDAO.createVendor(session, "vons", type);
            VendorDAO.createVendor(session, "trader joes", type);
            VendorDAO.createVendor(session, "safeway", type);

            List<Vendor> vendors = VendorDAO.searchVendorsByName(session, "a");
            Assert.assertEquals(vendors.size(), 2);
            vendors = VendorDAO.searchVendorsByName(session, "on");
            Assert.assertEquals(vendors.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
