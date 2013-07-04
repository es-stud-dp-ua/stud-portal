package ua.dp.stud.StudPortalLib.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
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

    public NewsDaoImplTest() {
	}

	@Autowired
	private NewsDao dao;
	private static News n1;
	private static News n2;
	private static Category cat1;
	private static Category cat2;

	@Before
	@Rollback(false)
	public void setUpClass() {
		cat1 = new Category("Sport");
		cat2 = new Category("General");
		n1 = new News("Тема1", "Текс1", "Автор1", new Date(), new Date(), cat1, true, true, true);
		n2 = new News("Тема2", "Текс2", "Автор2", new Date(), new Date(), cat2, false, false, false);
		dao.addCategory(cat1);
		dao.addCategory(cat2);
		dao.addNews(n1);
		dao.addNews(n2);
	}

    @Test
    public void calcPagesTest()
    {
        assertEquals(6, (int)dao.calcPages(21, 4));
    }

    @Test
    public void getAllNewsByAuthorTest()
    {
        List<News> expResult = (List) dao.getAllNewsByAuthor("Автор1");
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getCountByAuthorTest()
    {
        Integer result = 1;
        Integer expResult = dao.getCountByAuthor("Автор1");
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesNewsByAuthorTest()
    {
        List<News> expResult = (List) dao.getPagesNewsByAuthor("Автор1", 1, 4);
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n1.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getAllNewsByApprovedTest()
    {
        List<News> expResult = (List) dao.getAllNews(false);
        assertFalse(expResult.size() == 2);
        assertEquals(1, expResult.size());
        assertEquals(n2.getAuthor(), expResult.get(0).getAuthor());
    }

    @Test
    public void getCountByApprovedTest()
    {
        Integer result = 1;
        Integer expResult = dao.getCount(false);
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesNewsByApprovedTest()
    {
        List<News> expResult = (List) dao.getNewsOnPage(false, 1, 4);
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
		assertTrue(dao.addNews(n1).getId() == n1.getId());
		assertTrue(dao.addNews(n2).getId() == n2.getId());
	}

	/**
	 * Test of getNewsById method, of class NewsDaoImpl.
	 */
	@Test
	public void testGetNewsById() {
		assertNotNull(dao.getNewsById(n1.getId()));
		assertEquals(n1, dao.getNewsById(n1.getId()));
		assertTrue(n1.getInCalendar() == dao.getNewsById(n1.getId()).getInCalendar());
		assertEquals(n2, dao.getNewsById(n2.getId()));
		assertFalse(n2.getTopic().equals(dao.getNewsById(n1.getId()).getTopic()));
	}

	/**
	 * Test of updateNews method, of class NewsDaoImpl.
	 */
//	@Test
//	public void testUpdateNews() {
//		News n3 = new News("Тема3", "Текс3", "Автор3", new Date(), new Date(), cat1, 1, 1, 1);
//        n3.setAuthor("Автор4");
//        dao.updateNews(n3);
//		assertTrue( n3.getAuthor()!= "Автор3");
//	}

	/**
	 * Test of deleteNews method, of class NewsDaoImpl.
	 */
	/*@Test
	public void testDeleteNews() {
		News n3 = new News("Тема3", "Текс3", "Автор3", new Date(), new Date(), cat1, 1, 1, 1);
        n3.setId(10);
		dao.addNews(n3);
		dao.deleteNews(10);
		assertNull(dao.getNewsById(10));
	}*/

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
	public void testUpdateCategory() {
		assertTrue(dao.updateCategory(cat1.getId(), "Наука") == 1);
		assertFalse(dao.updateCategory(3, "Знакомства") == 1);
	}

	@Test
	public void testDeleteCategory() {
		assertTrue(dao.deleteCategory(cat1.getId()) == 1);
		assertTrue(dao.deleteCategory(3) == 0);
	}

	@Test
	public void getNewsOnMainPage() {
		Collection<News> fromDao = dao.getNewsOnMainPage();

		assertTrue(fromDao.contains(n1));
		assertEquals(1, fromDao.size());
	}
	@Test
	public void getNewsOnPage()
	{
		Collection<News> fromDao = dao.getNewsOnPage(1, 10);
  
		assertNotNull(fromDao);
		assertTrue(fromDao.contains(n1));
		assertEquals(2, fromDao.size());

		fromDao = null;
		fromDao = dao.getNewsOnPage(2, 10);

		assertNotNull(fromDao);
		assertFalse(fromDao.contains(n2));
		assertEquals(0, fromDao.size());
	}
	@Test
	public void getAllCategories()
	{
		Collection<Category> fromDao = dao.getAllCategories();
		assertTrue(fromDao.contains(cat1));
		assertEquals(2, fromDao.size());
	}
	@Test
	public void addImage()
	{
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
        dao.updateNews(n1);
        n1 = null;
        n1 = dao.getNewsById(news_id);
        assertNotNull(n1);
        assertNotNull(n1.getMainImage());
        assertNotNull(n1.getAdditionalImages());
        assertEquals(2, n1.getAdditionalImages().size());
        assertEquals(additionalImages, n1.getAdditionalImages());
    }

	@After
	@Rollback(false)
	public void clearTestDB() {
		dao.deleteCategory(cat1.getId());
		dao.deleteCategory(cat2.getId());
	}
}