package ua.dp.studportal.calendarportlet.controller;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import ua.dp.studportal.calendarportlet.service.CalendarService;

import javax.portlet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * VIEW mode for calendar controller
 */
@RequestMapping(value = "VIEW")
@Controller(value = "calendarController")
public class CalendarController {
    private static final int MONTH_LOCALIZATION_DELTA = 10;
    private static final String NEWS_ARCHIVE_REFERENCE_NAME = "NewsArchive_WAR_studnewsArchive";
    private static final Logger LOG = Logger.getLogger(CalendarController.class.getName());

    @Autowired
    @Qualifier("calendarService")
    private CalendarService calendarService;

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * Returns ModelAndView with "home" - view name and bunch of content in model:
     * map with news for current month, list with number of days of week,
     * number of month, number of  year. This method is called when the first time
     * calendar portlet appears on the browser page or/and on the refresh of this page
     *
     * @param response
     * @param request
     * @param month     int month
     * @param year      int year
     * @param direction one of possible directions: "thisMonth", "prevMonth", "nextMonth"
     * @return ModelAndView
     */
    @RenderMapping
    public ModelAndView showCalendar(RenderResponse response, RenderRequest request,
                                     @RequestParam(required = true, defaultValue = "-1") int month,
                                     @RequestParam(required = true, defaultValue = "-1") int year,
                                     @RequestParam(required = true, defaultValue = "thisMonth") String direction) {

        int[] currentDate = getCurrentDateFromRequest(month, year);
        DateTime dateTime = new DateTime(currentDate[1], currentDate[0], 1, 0, 0);
        Map<String, Object> model = getModelFilledByTime(dateTime, direction);
        setPlid(request, model);

        return new ModelAndView("home", model);
    }


    /**
     * AJAX RESPONSE. Returns ModelAndView with "calendar_only" - view name and bunch of content in model:
     * map with news for month depending on direction, list with number of days of week,
     * number of month, number of  year. This method is called whenever user wants to see news
     * for the next or previous month and reload only just calendar portlet not a whole page.
     *
     * @param response
     * @param request
     * @param month     int month
     * @param year      int year
     * @param direction one of possible directions: "thisMonth", "prevMonth", "nextMonth"
     * @return ModelAndView
     */
    @ResourceMapping(value = "next")
    public ModelAndView renderCalendar(ResourceResponse response, ResourceRequest request,
                                       @RequestParam(required = true, defaultValue = "-1") int month,
                                       @RequestParam(required = true, defaultValue = "-1") int year,
                                       @RequestParam(required = true, defaultValue = "thisMonth") String direction) {
        int[] currentDate = getCurrentDateFromRequest(month, year);
        DateTime dateTime = new DateTime(currentDate[1], currentDate[0], 1, 0, 0);
        Map<String, Object> model = getModelFilledByTime(dateTime, direction);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        setPlid(request, model);

        return new ModelAndView("calendar_only", model);

    }

    /**
     * Get the map with day=newsmodel for specified month and year and sends it to jsp page
     *
     * @param month integer from 0 to 11
     * @param year  integer year
     * @return int array with month(at 0 index) and year(at 1 index)
     */
    private int[] getCurrentDateFromRequest(int month, int year) {
        if (year == -1 || month == -1) {
            Calendar calendar = Calendar.getInstance();
            return new int[]{calendar.get(calendar.MONTH) + 1, calendar.get(Calendar.YEAR)};
        }
        return new int[]{month, year};
    }

    /**
     * Generates list filled up with days of week numbers.
     * like 1-4 where 1 is day of month and 4 is day of week
     *
     * @param dateTime object of current time
     * @return list days of week
     */
    private List<Long> getDaysOfWeek(DateTime dateTime) {
        DateTime dt = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
        List<Long> daysOfWeek = new ArrayList<Long>();
        daysOfWeek.add(0L);
        int totalDays = dt.dayOfMonth().getMaximumValue();
        for (int i = 0; i < totalDays; i++) {
            daysOfWeek.add((long) dt.plusDays(i).dayOfWeek().get());
        }
        return daysOfWeek;
    }

    /**
     * This method is created to reduce duplicated lines
     * in renderCalendar and showCalendar method
     *
     * @param dateTime  object of current month
     * @param direction one of possible directions: "thisMonth", "prevMonth", "nextMonth"
     * @return model which should be passed to ModelAndView as model
     */
    private Map<String, Object> getModelFilledByTime(DateTime dateTime, String direction) {
        DateTime dt = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0);
        if (direction.equals("nextMonth")) {
            dt = dt.plus(Period.months(1));
        } else if (direction.equals("prevMonth")) {
            dt = dt.minus(Period.months(1));
        } else if (direction.equals("nextYear")) {
            dt = dt.plus(Period.years(1));
        } else if (direction.equals("lastYear")) {
            dt = dt.minus(Period.years(1));
        }
        Map<String, Object> model = new LinkedHashMap<String, Object>();
        List<Long> daysInWeek = getDaysOfWeek(dt);
        //in jodaTime first month has number 1, default calendar 0. That's why we decrement month
        model.put("news", calendarService.getCalNewsForMonth(dt.getMonthOfYear() - 1, dt.getYear()));
        model.put("days", daysInWeek);
        model.put("currentMonth", dt.getMonthOfYear());
        model.put("monthToShow", dt.getMonthOfYear() + MONTH_LOCALIZATION_DELTA);
        model.put("currentYear", dt.getYear());
        return model;
    }


    /**
     * plid is needed for portlet communication
     *
     * @param request
     * @param model
     */
    public void setPlid(PortletRequest request, Map<String, Object> model) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        Long groupId = themeDisplay.getScopeGroupId();
        Long plid = 0l;
        try {
            plid = LayoutLocalServiceUtil.getDefaultPlid(groupId, false, NEWS_ARCHIVE_REFERENCE_NAME);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Exception: ", ex);
        }
        model.put("plid", plid);
        model.put("archive_name", NEWS_ARCHIVE_REFERENCE_NAME);
    }
}
