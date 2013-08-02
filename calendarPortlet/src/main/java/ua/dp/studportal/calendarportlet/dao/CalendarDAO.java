package ua.dp.studportal.calendarportlet.dao;

import ua.dp.stud.StudPortalLib.model.News;

import java.util.ArrayList;
import java.util.Map;

/**
 * Calendar DAO
 *
 * @author Alex
 */
public interface CalendarDAO {
    /**
     * @param month number of month starting from 0 (January)to 11(December)
     * @param year  int year
     * @return HashMap\<Integer, ArrayList\<NewsModel\>\> where key is day of month and List is list
     *         of all news in calendar for that day
     */
    Map<Long, ArrayList<News>> getCalNewsForMonth(int month, int year);
}
