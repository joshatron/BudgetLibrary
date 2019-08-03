package io.joshatron.budgetlibrary.database;

import io.joshatron.budgetlibrary.dtos.Type;
import io.joshatron.budgetlibrary.exception.BudgetLibraryException;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TypeDAOTest {
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
    public void createTypeBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            Assert.assertEquals(type.getName(), "groceries");
            Assert.assertEquals(type.getDescription(), "food");

            List<Type> types = TypeDAO.getTypes(session, null, null);
            Assert.assertEquals(types.size(), 1);
            Assert.assertEquals(types.get(0), type);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void updateTypeBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            TypeDAO.updateType(session, type.getId(), "market", "stuff");

            List<Type> types = TypeDAO.getTypes(session, null, null);
            Assert.assertEquals(types.size(), 1);
            Assert.assertEquals(types.get(0).getName(), "market");
            Assert.assertEquals(types.get(0).getDescription(), "stuff");
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void deleteTypeBasic() {
        try {
            Type type = TypeDAO.createType(session, "groceries", "food");
            TypeDAO.deleteType(session, type.getId());

            List<Type> types = TypeDAO.getTypes(session, null, null);
            Assert.assertEquals(types.size(), 0);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getTypesByName() {
        try {
            TypeDAO.createType(session, "groceries", "food");
            TypeDAO.createType(session, "market", "food");
            TypeDAO.createType(session, "gas", "oil");

            List<Type> types = TypeDAO.getTypes(session, "groceries", null);
            Assert.assertEquals(types.size(), 1);
            types = TypeDAO.getTypes(session, "market", null);
            Assert.assertEquals(types.size(), 1);
            types = TypeDAO.getTypes(session, "gas", null);
            Assert.assertEquals(types.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }

    @Test
    public void getTypesByDescription() {
        try {
            TypeDAO.createType(session, "groceries", "food");
            TypeDAO.createType(session, "market", "food");
            TypeDAO.createType(session, "gas", "oil");
            TypeDAO.createType(session, "amazon", "stuff");

            List<Type> types = TypeDAO.getTypes(session, null, "food");
            Assert.assertEquals(types.size(), 2);
            types = TypeDAO.getTypes(session, null, "oil");
            Assert.assertEquals(types.size(), 1);
            types = TypeDAO.getTypes(session, null, "stuff");
            Assert.assertEquals(types.size(), 1);
        } catch(BudgetLibraryException e) {
            Assert.fail("Got exception: " + e.getCode());
        }
    }
}
