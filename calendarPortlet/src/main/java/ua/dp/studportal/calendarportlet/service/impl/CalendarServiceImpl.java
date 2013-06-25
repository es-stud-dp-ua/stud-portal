package ua.dp.studportal.calendarportlet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.studportal.calendarportlet.dao.CalendarDAO;
import ua.dp.studportal.calendarportlet.service.CalendarService;

import java.util.ArrayList;
import java.util.Map;

/**
 * calendar service
 */
@Service("calendarService")
public class CalendarServiceImpl implements CalendarService
{
    @Autowired
    private CalendarDAO calendarDAO;

    public CalendarServiceImpl()
    {}

	public void setCalendarDAO(CalendarDAO calendarDAO) {
		this.calendarDAO = calendarDAO;
	}

	/**
     * Get the map with day=newsmodel for specified month and year from database using DAO
     *
     *
     * @param month  integer from 0 to 11
     * @param year  integer year
     * @return map with long (day number) as key and list of newsmodel as value, if no news for that day
     *          list will be empty
     */
    @Transactional
    public Map<Long, ArrayList<News>> getCalNewsForMonth(int month, int year) {
        return calendarDAO.getCalNewsForMonth(month,year);
    }
}
