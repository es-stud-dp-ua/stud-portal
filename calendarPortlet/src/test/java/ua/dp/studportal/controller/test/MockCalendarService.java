package ua.dp.studportal.controller.test;

import org.springframework.stereotype.Service;
import ua.dp.stud.StudPortalLib.model.Category;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.studportal.calendarportlet.service.CalendarService;
import ua.dp.studportal.calendarportlet.util.MapInitializer;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 10.11.12
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
@Service("mockCalendarService")
public class MockCalendarService implements CalendarService {
    private ArrayList<News> testContent;

    public MockCalendarService() {
        init();
    }

    private void init() {
        testContent = new ArrayList<News>();
        Category cat1 = new Category("General");
        Calendar cal = new GregorianCalendar(1980, 2, 1, 12, 23, 53);
        News oldNews = new News("topic", "this is old in cal news", "jjdev", new Date(),
                cal.getTime(), cat1, true, true, true);
        News notInCal = new News("topic", "this is old not in cal news", "jjdev", new Date(), cal.getTime(),
                cat1, true, false, true);
        cal = new GregorianCalendar(2012, 3, 3, 12, 23, 53);
        News newNews = new News("topic", "this is new news", "jjdev", new Date(), cal.getTime(),
                cat1, true, true, true);

        testContent.add(oldNews);
        testContent.add(notInCal);
        testContent.add(newNews);
    }

    public Map<Long, ArrayList<News>> getCalNewsForMonth(int month, int year) {
        Calendar startDate = new GregorianCalendar(year, month, 1, 0, 0, 0);
        int daysInMonth = startDate.getActualMaximum(5);
        Calendar endDate = new GregorianCalendar(year, month, daysInMonth, 23, 59, 59);
        Map<Long, ArrayList<News>> toReturn = MapInitializer.initMap(daysInMonth);
        for (News newsModel : testContent) {
            if (newsModel.getPublication().after(startDate.getTime()) &&
                    newsModel.getPublication().before(endDate.getTime()) && newsModel.getInCalendar() == true) {
                Calendar cal = new GregorianCalendar();
                cal.setTime(newsModel.getPublication());
                toReturn.get((long) cal.get(Calendar.DAY_OF_MONTH)).add(newsModel);
            }
        }
        return toReturn;
    }
}
