package ua.dp.stud.StudPortalLib.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Roman Lukash
 * @author Vladislav Pikus
 */

@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String TEST_TEXT = "TEST test TEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST test"
            + "TEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST test"
            + "TEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST testTEST test";

    public NewsDaoImplTest() {
    }

    @Autowired
    private NewsDao dao;
    private static News n1;
    private static News n2;

    @Before
    @Rollback(false)
    public void setUpClass() {
        n1 = new News("Тема1", TEST_TEXT, "Автор1", new Date(123), new Date(123), null, true, true, true);
        n2 = new News("Тема2", TEST_TEXT, "Автор2", new Date(123), new Date(123), null, false, false, false);
        dao.save(n1);
        dao.save(n2);
    }

    @Test
    public void calcPagesTest() {
        assertEquals(6, (int) dao.calcPages(21, 4));
    }

    @Test
    public void getNumberOfViewsTest() {
        News news = dao.getById(n1.getId());
        Integer result = 0;
        assertEquals(result, news.getNumberOfViews());
    }

    @Test
    public void getAllNewsByAuthorTest() {
        List<News> expResult = (List) dao.getAllNewsByAuthor("Автор1");
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getCountByAuthorTest() {
        Integer result = 1;
        Integer expResult = dao.getCountByAuthor("Автор1");
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesNewsByAuthorTest() {
        List<News> expResult = (List) dao.getPagesObjectByAuthor("Автор1", 1, 4);
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getAllNewsByApprovedTest() {
        List<News> expResult = (List) dao.getAllNews(false);
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n2.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getCountByApprovedTest() {
        Integer result = 2;
        Integer expResult = dao.getCount();
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesNewsByApprovedTest() {
        List<News> expResult = (List) dao.getObjectOnPage(false, 1, 4);
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n2.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void testAddCategory() {
        assertEquals(dao.getAllNews().size(), 2);
    }

    /**
     * Test of addNews method, of class NewsDaoImpl.
     */
    @Test
    public void testAddNews() {
        // assertTrue(dao.addObject(n1).getId() == n1.getId());
        // assertTrue(dao.addObject(n2).getId() == n2.getId());
    }

    /**
     * Test of getNewsById method, of class NewsDaoImpl.
     */
    @Test
    public void testGetNewsById() {
        assertNotNull(dao.getById(n1.getId()));
        assertEquals(n1, dao.getById(n1.getId()));
        assertEquals(n2, dao.getById(n2.getId()));
    }

    /**
     * Test of updateNews method, of class NewsDaoImpl.
     */
    @Test
    public void testUpdateNews() {
        n2.setAuthor("Автор4");
        dao.update(n2);
        assertTrue(n2.getAuthor() == "Автор4");
    }

    /**
     * Test of deleteNews method, of class NewsDaoImpl.
     */
    @Test
    public void testDeleteNews() {
        Integer id = n2.getId();
        dao.delete(id);
        assertNull(dao.getById(id));
    }

    /**
     * Test of getAllNews method, of class NewsDaoImpl.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetAllNews() {
        List<News> coll = (List) dao.getAllNews();
        assertFalse(coll.size() == 10);
        assertEquals(2, coll.size());
        assertEquals(n1.getAuthor(), coll.get(0).getAuthor());
    }

    /**
     * Test of getCount method, of class NewsDaoImpl.
     */
    @Test
    public void testGetCount() {
        assertTrue(dao.getCount() == 2);
        assertFalse(dao.getCount() == 10);
    }

    @Test
    public void getNewsOnMainPage() {
        Collection<News> fromDao = dao.getNewsOnMainPage();

        assertTrue(fromDao.contains(n1));
        assertEquals(1, fromDao.size());
    }

    @Test
    public void getNewsOnPage() {
        Collection<News> fromDao = dao.getNewsOnPage(1, 10);

        assertNotNull(fromDao);
        assertTrue(fromDao.contains(n1));
        assertEquals(2, fromDao.size());

        fromDao = dao.getNewsOnPage(2, 10);

        assertNotNull(fromDao);
        assertFalse(fromDao.contains(n2));
        assertEquals(0, fromDao.size());
    }

    @Test
    public void addImage() {
        ImageImpl image = new ImageImpl();
        dao.addImage(image);
    }

    @Test
    public void getSetAdditionalImages() {
        ImageImpl mainImage = new ImageImpl();
        final List<ImageImpl> additionalImages = new ArrayList<ImageImpl>();
        additionalImages.add(new ImageImpl());
        additionalImages.add(new ImageImpl());
        n1.setMainImage(mainImage);
        n1.setAdditionalImages(additionalImages);
        int news_id = n1.getId();
        dao.update(n1);
        n1 = null;
        n1 = (News) dao.getById(news_id);
        assertNotNull(n1);
        assertNotNull(n1.getMainImage());
        assertNotNull(n1.getAdditionalImages());
        assertEquals(2, n1.getAdditionalImages().size());
        assertEquals(additionalImages, n1.getAdditionalImages());
    }
}