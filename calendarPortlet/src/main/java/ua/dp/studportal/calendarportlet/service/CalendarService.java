package ua.dp.studportal.calendarportlet.service;

import ua.dp.stud.StudPortalLib.model.News;

import java.util.ArrayList;
import java.util.Map;

/**
 * Calendar Interface
 *
 * @author Alex
 */
public interface CalendarService {
    /**
     * Return the same as calendar DAO
     *
     * @param month
     * @param year
     * @return
     */
    Map<Long, ArrayList<News>> getCalNewsForMonth(int month, int year);
}
