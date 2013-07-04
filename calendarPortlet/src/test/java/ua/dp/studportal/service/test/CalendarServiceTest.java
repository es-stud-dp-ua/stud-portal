package ua.dp.studportal.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.studportal.calendarportlet.dao.CalendarDAO;
import ua.dp.studportal.calendarportlet.service.impl.CalendarServiceImpl;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:calendarServiceTest.xml")
public class CalendarServiceTest {

    private CalendarDAO mockDao;

    private CalendarServiceImpl calendarService;

    @Test
    public void testGetCalNewsForMonth() {
        calendarService = new CalendarServiceImpl();
        mockDao = mock(CalendarDAO.class);
        when(mockDao.getCalNewsForMonth(1, 1980)).thenReturn(initMap(31));
        calendarService.setCalendarDAO(mockDao);
        Map<Long, ArrayList<News>> mapWithNews = initMap(30);
        Calendar cal = Calendar.getInstance();


        Category cat1 = new Category("General");

        News expected = new News("topic", "this is old in cal news", "jjdev", new Date(),
                cal.getTime(), cat1, true, true, true);

        mapWithNews.get((long) 4).add(expected);
        when(mockDao.getCalNewsForMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))).thenReturn(mapWithNews);
        Map<Long, ArrayList<News>> gettedMap = calendarService.getCalNewsForMonth(1, 1980);
        for (ArrayList<News> newsForDay : gettedMap.values()) {
            assertEquals(0, newsForDay.size());
        }
        gettedMap = calendarService.getCalNewsForMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        assertEquals(1, gettedMap.get(Long.valueOf(4)).size());
        assertEquals(expected, gettedMap.get(Long.valueOf(4)).get(0));
    }

    private Map<Long, ArrayList<News>> initMap(int daysInMonth) {
        Map<Long, ArrayList<News>> toReturn = new LinkedHashMap<Long, ArrayList<News>>();
        for (int i = 1; i <= daysInMonth; i++) {
            toReturn.put(Long.valueOf(i), new ArrayList<News>());
        }
        return toReturn;
    }
}
