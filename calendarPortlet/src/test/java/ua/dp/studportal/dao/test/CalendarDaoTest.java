package ua.dp.studportal.dao.test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.studportal.calendarportlet.dao.CalendarDAO;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(locations = "classpath:calendarDaoTest.xml")
public class CalendarDaoTest extends AbstractTransactionalJUnit4SpringContextTests
{
    private News oldNews;
    private News newNews;
    private Category cat1;
    private Category cat2;

    @Autowired
    private CalendarDAO calendarDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    @Test
    @Rollback(true)
    public void testGetCalNewsForMonth()
    {
        cat1=new Category("General");
        cat2=new Category("Sports");
        Calendar cal = new GregorianCalendar(1980, 2, 1, 12, 23, 53);
        oldNews = new News("topic","this is old in cal news","jjdev",cal.getTime(),cal.getTime(),cat1,true,1,1);

        News notInCal = new News("topic","this is old not in cal news","/",cal.getTime(),cal.getTime(),cat1,true,0,1);

        cal = new GregorianCalendar(2012, 3, 3, 12, 23, 53);
        newNews = new News("topic","this is new news","jjdev",cal.getTime(),cal.getTime(),cat1,true,1,1);
        getSessionFactory().getCurrentSession().save(cat1);
        getSessionFactory().getCurrentSession().save(oldNews);
        getSessionFactory().getCurrentSession().save(notInCal);
        getSessionFactory().getCurrentSession().save(newNews);
        Map<Long, ArrayList<News>> gettedMap = calendarDAO.getCalNewsForMonth(2,1980);
        assertEquals(oldNews, gettedMap.get(Long.valueOf(1)).get(0));
        assertEquals(31, gettedMap.size());
        gettedMap = calendarDAO.getCalNewsForMonth(3,2012);
        assertEquals(newNews, gettedMap.get(Long.valueOf(3)).get(0));
        assertEquals(30, gettedMap.size());
    }
}
