package ua.dp.studportal.calendarportlet.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.studportal.calendarportlet.util.MapInitializer;

import java.util.*;

/**
 * Calendar DAO implementation
 */
@Repository("calendarDAO")
public class CalendarDAOImpl implements CalendarDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    private static final int MAX_HOURS = 23;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get the map with day=list of newsmodel for specified month and year from database
     *
     * @param month  integer from 0 to 11
     * @param year  integer year
     * @return map with long (day number) as key and list of newsmodel as value, if no news for that day
     *          list will be empty
     */
    @Transactional
    public Map<Long, ArrayList<News>> getCalNewsForMonth(int month, int year) {
        Calendar startDate = new GregorianCalendar(year, month, 1, 0,0,0);
        int daysInMonth = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar endDate = new GregorianCalendar(year,month,daysInMonth,MAX_HOURS,MAX_SECONDS,MAX_MINUTES);
        List<News> calNews = sessionFactory.getCurrentSession().createCriteria(News.class)
                .add(Restrictions.between("publicationInCalendar", startDate.getTime(), endDate.getTime()))
                .add(Restrictions.eq("inCalendar",1)).add(Restrictions.eq("approved",true)).list();
        //todo: why set is used?
		Set<News> setcalNews = new LinkedHashSet<News>();
        for (News news : calNews)
        {
            setcalNews.add(news);
        }
        Map<Long, ArrayList<News>>  toReturn = MapInitializer.initMap(daysInMonth);
        Iterator<News> iter = setcalNews.iterator();
        //todo: replace while loop by foreach
        while (iter.hasNext())
        {
            News oneNews  = iter.next();
            Calendar cal = Calendar.getInstance();
            cal.setTime(oneNews.getPublicationInCalendar());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            ArrayList<News> list = toReturn.get((long)day);
            if (list == null)
            {
                list = new ArrayList<News>();
            }
            list.add(oneNews);
            toReturn.put((long)day,list);
        }
        return toReturn;
    }
}
