package ua.dp.studportal.calendarportlet.util;

import ua.dp.stud.StudPortalLib.model.News;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Simple class
 *  used to Init map for DAO
 */
public final class MapInitializer
{
    private MapInitializer()
    {}

    /**
     * Init map for DAO
     *
     * @param daysInMonth    number of days in cur month
     * @return
     */
    public static Map<Long, ArrayList<News>> initMap(int daysInMonth)
    {
        Map<Long, ArrayList<News>>  toReturn = new LinkedHashMap<Long, ArrayList<News>>();
        for (int i = 1; i <= daysInMonth; i++)
        {
            toReturn.put((long)i,new ArrayList<News>());
        }
        return toReturn;
    }
}
