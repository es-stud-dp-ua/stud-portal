package ua.dp.stud.StudPortalLib.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.impl.NewsServiceImpl;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDaoImpl;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    private NewsService service;
    private NewsDao mockDao;
    private News news1, news2;
    private Category cat1, cat2;

    @Before
    public void init() {
        service = new NewsServiceImpl();
        mockDao = mock(NewsDao.class);
        ((NewsServiceImpl) service).setDao(mockDao);

        cat1 = new Category("cat1");
        cat2 = new Category("cat2");

        news1 = new News("topic1", "text1", "author1", new Date(), new Date(), cat1, true, true, true);
        news1.setId(1);

        news2 = new News("topic2", "text2", "author2", new Date(), new Date(), cat2, false, false, false);
        news2.setId(2);
    }

    @Test
    public void getAllNewsByAuthorTest() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news1));
        when(mockDao.getAllNewsByAuthor("author1")).thenReturn(allNews);

        assertEquals(allNews, service.getAllNewsByAuthor("author1"));

        when(mockDao.getAllNewsByAuthor("author10")).thenReturn(null);
        assertNull(service.getAllNewsByAuthor("author10"));
    }

    @Test
    public void getAllNewsByApprovedTest() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news2));
        when(mockDao.getAllNews(false)).thenReturn(allNews);

        assertEquals(allNews, service.getAllNews(false));
    }

    @Test
    public void getPagesCountByAuthorTest() {
        when(mockDao.getCountByAuthor("author1")).thenReturn(10);
        when(mockDao.calcPages(10, 4)).thenReturn(3);
        Integer result = 3;
        Integer expResult = service.getPagesCountByAuthor("author1", 4);
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesCountByApprovedTest() {
        when(mockDao.getCount(false)).thenReturn(10);
        when(mockDao.calcPages(10, 4)).thenReturn(3);
        Integer result = 3;
        Integer expResult = service.getPagesCount(false, 4);
        assertEquals(result, expResult);
    }

    @Test
    public void getPagesNewsByAuthorTest() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news1));
        when(mockDao.getPagesObjectByAuthor("author1", 1, 4)).thenReturn(allNews);
        assertEquals(allNews, service.getPagesNewsByAuthor("author1", 1, 4));
    }

    @Test
    public void getPagesNewsByApprovedTest() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news2));
        when(mockDao.getObjectOnPage(false, 1, 4)).thenReturn(allNews);
        assertEquals(allNews, service.getNewsOnPage(false, 1, 4));
    }

    /**
     * Test getNewsById method
     */
    @Test
    public void testGetNewsById() {
        when(mockDao.getById(1)).thenReturn(news1);
        assertEquals(news1, service.getNewsById(1));

        assertNull(service.getNewsById(0));
    }

    /**
     * Test getNewsByTopic method
     */
    @Test
    public void testGetAllNews() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news1, news2));
        when(mockDao.getAllNews()).thenReturn(allNews);
        assertEquals(allNews, service.getAllNews());

        when(mockDao.getAllNews()).thenReturn(null);
        assertNull(service.getAllNews());
    }

    /**
     * Test getNewsOnPage
     */
    @Test
    public void testGetNewsOnPage() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news1, news2));
        when(mockDao.getNewsOnPage(1, 10)).thenReturn(allNews);
        assertEquals(allNews, service.getNewsOnPage(1, 10));
    }

    /**
     * Test getNewsOnPage when no news in DB
     */
    @Test
    public void testGetPagesCountEmptyNews() {
        when(mockDao.getCount()).thenReturn(0);
        assertEquals(0, (int) service.getPagesCount(10));
    }

    /**
     * Test getNewsOnPage
     */
    @Test
    public void testGetPagesCount() {
        when(mockDao.getCount()).thenReturn(5);
        when(mockDao.calcPages(5, 10)).thenReturn(1);
        assertEquals(1, (int) service.getPagesCount(10));

        when(mockDao.getCount()).thenReturn(15);
        when(mockDao.calcPages(15, 10)).thenReturn(2);
        assertEquals(2, (int) service.getPagesCount(10));

        when(mockDao.getCount()).thenReturn(20);
        when(mockDao.calcPages(20, 20)).thenReturn(1);
        assertEquals(1, (int) service.getPagesCount(20));
    }

    /**
     * Test addNews
     */
    @Test
    public void testAddNews() {
        service.addNews(news1);
        verify(mockDao, times(1)).save(news1);
    }

    /**
     * Test updateNews
     */
    @Test
    public void testUpdateNews() {
        service.updateNews(news1);
        verify(mockDao, times(1)).update(news1);
    }

    /**
     * Test getNewsByTopic method
     */
    @Test
    public void testGetNewsOnMainPage() {
        LinkedList<News> allNews = new LinkedList<News>(Arrays.asList(news1));
        when(mockDao.getNewsOnMainPage()).thenReturn(allNews);
        assertEquals(allNews, service.getNewsOnMainPage());
    }

    @Test
    public void testAddImage() {
        ImageImpl image = new ImageImpl();
        service.addImage(image);
        verify(mockDao, times(1)).addImage(image);
    }

    @Test
    public void testGetAllCategories() {
        LinkedList<Category> allCat = new LinkedList<Category>(Arrays.asList(cat1, cat2));
        when(mockDao.getAllCategories()).thenReturn(allCat);
        assertEquals(allCat, service.getAllCategories());
    }

    @Test
    public void testDeleteNews() {
        service.addNews(news1);
        Integer id = news1.getId();
        service.deleteNews(news1);
        verify(mockDao, times(1)).delete(id);
    }
}