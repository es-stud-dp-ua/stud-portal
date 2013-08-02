package ua.dp.stud.StudPortalLib.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.dao.OrganizationDao;
import ua.dp.stud.StudPortalLib.util.OrganizationType;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrganizationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private OrganizationService service;
    private static Organization org1;
    private static Organization org2;
    private static Organization org3;
    private OrganizationDao mockDao;

    @Before
    @Rollback(false)
    public void setUpClass() {
        org1 = new Organization();
        org2 = new Organization();
        org3 = new Organization();
        org1.setOrganizationType(OrganizationType.SPORTS);
        org1.setTitle("Sport org");
        org1.setText("We are sport!");
        org1.setAuthor("author1");
        org2.setOrganizationType(OrganizationType.OTHERS);
        org2.setTitle("Other org");
        org2.setText("We are other!");
        org2.setAuthor("author1");
        org1.setApproved(Boolean.TRUE);
        org2.setApproved(Boolean.TRUE);
        org3.setOrganizationType(OrganizationType.OTHERS);
        org3.setApproved(Boolean.TRUE);
        org3.setAuthor("test");
        org3.setTitle("test123");
        String TEST_TEXT = "sadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdfhsflhkfsdkgnsdjfhgbsdjhgdasdfsdgfdhfgjsadgfsdf";

        org1.setText(TEST_TEXT);
        org2.setText(TEST_TEXT);
        org3.setText(TEST_TEXT);
        org1.setPublication(new Date(123));
        org2.setPublication(new Date(123));
        org3.setPublication(new Date(123));
        service.addOrganization(org1);
        service.addOrganization(org2);
    }

    @Test
    public void getOrganizationsOnPageTest() {
        List<Organization> expResult = (List) service.getOrganizationsOnPage(true, 1, 4);
        assertFalse(expResult.size() == 1);
        assertEquals(2, expResult.size());
        assertEquals(org1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getAllOrganizationByAuthorTest() {
        List<Organization> expResult = (List) service.getAllOrganizationByAuthor("author1");
        assertFalse(expResult.size() == 1);
        assertEquals(2, expResult.size());
        assertEquals(org1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getCountByAuthorTest() {
      ///  Integer result = 1;
      //  Integer expResult = service.getPagesCountByAuthor("author1", 4);
      //  assertEquals(result, expResult);
    }

    @Test
    public void getPagesOrganizationByAuthorTest() {
        List<Organization> expResult = (List) service.getPagesOrganizationByAuthor("author1", 1, 4);
        assertFalse(expResult.size() == 1);
        assertEquals(2, expResult.size());
        assertEquals(org1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getPagesCountTest() {
        Integer result = 1;
        Integer expResult = service.getPagesCount(true, 4);
        assertEquals(result, expResult);
    }

    @Test
    public void AddOrg() {
        assertNull(org3.getId());
        service.addOrganization(org3);
        assertNotNull(org3.getId());
    }

    @Test
    public void getById() {
        Integer id = org1.getId();
        assertEquals(org1, service.getOrganizationById(id));
    }

    @Test
    public void getAll() {
        Set<Organization> orgsList = new HashSet<Organization>(Arrays.asList(org1, org2));
        Set<Organization> fromDao = new HashSet<Organization>(service.getAllOrganizations(true));

        assertEquals(orgsList, fromDao);
        assertEquals(2, fromDao.size());
    }

    @Test
    public void getOrganizationTypes() {
        assertNotNull(service.getOrganizationTypes());
        assertEquals(7, service.getOrganizationTypes().size());
    }

    @Test
    public void getByType() {
        OrganizationType type = OrganizationType.SPORTS;
        List<Organization> sportOrgs = (List<Organization>) service.getOrganizationsByType(type.toString());
        assertEquals(1, sportOrgs.size());
        assertEquals(org1, sportOrgs.get(0));
    }

    @Test
    public void getByWrongType() {
        List<Organization> sportOrgs = (List<Organization>) service.getOrganizationsByType("noexistingcategory");
        assertNull(sportOrgs);
    }

    @Test
    public void pagination() {
        service.addOrganization(org3);
        List<Organization> othersList = (List<Organization>) service.getOrganizationsOnPage(1, 1, org3.getOrganizationType().toString(), true);
        assertNull(service.getOrganizationsOnPage(1, 1, "type1", true));
    }

    /* @Test
     public void delete() {
     service.addOrganization(org1);
     Integer id = org1.getId();
     service.deleteOrganization(org1);
     verify(mockDao, times(1)).deleteOrganization(id);
     }*/
    @Test
    public void update() {
        Integer id = org1.getId();
        org1.setOrganizationType(OrganizationType.OTHERS);
        service.updateOrganization(org1);
        Organization org = service.getOrganizationById(id);
        assertEquals(OrganizationType.OTHERS, org.getOrganizationType());
    }

    @Test
    public void getCount() {
        assertEquals(2, (int) service.getCount());
    }
}
