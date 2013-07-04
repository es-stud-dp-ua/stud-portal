package ua.dp.studportal.controller.test;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.mock.web.portlet.MockResourceResponse;
import org.springframework.web.portlet.ModelAndView;
import ua.dp.studportal.calendarportlet.controller.CalendarController;
import ua.dp.studportal.calendarportlet.service.CalendarService;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LayoutLocalServiceUtil.class)
public class CalendarControllerTest {
    private final long GROUP_ID = 1;
    private final String PORTLET_ID = "NewsArchive_WAR_studnewsArchive";
    private final long PAGE_ID = 2;
    private ThemeDisplay mockThemeDisplay;

    private CalendarController calendarController = new CalendarController();
    private CalendarService calendarService = new MockCalendarService();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(LayoutLocalServiceUtil.class);
        mockThemeDisplay = mock(ThemeDisplay.class);
        calendarController.setCalendarService(calendarService);
        try {
            when(LayoutLocalServiceUtil.getDefaultPlid(GROUP_ID, false, PORTLET_ID)).thenReturn(PAGE_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testShowCalendar() {
        MockRenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);

        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
        when(mockThemeDisplay.getScopeGroupId()).thenReturn(GROUP_ID);
        ModelAndView model = null;

        try {
            model = calendarController.showCalendar(response, request, 11, 2012, "nextMonth");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assertEquals("home", model.getViewName());
        assertEquals(PAGE_ID, model.getModel().get("plid"));
        assertEquals(12, model.getModel().get("currentMonth"));
        assertEquals(2012, model.getModel().get("currentYear"));
    }

    @Test
    public void testRenderCalendar() {
        MockResourceResponse response = new MockResourceResponse();
        ResourceRequest request = mock(ResourceRequest.class);

        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
        when(mockThemeDisplay.getScopeGroupId()).thenReturn(GROUP_ID);
        ModelAndView model = null;

        try {
            model = calendarController.renderCalendar(response, request, 11, 2012, "prevMonth");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assertEquals("calendar_only", model.getViewName());
        assertEquals(PAGE_ID, model.getModel().get("plid"));
        assertEquals(10, model.getModel().get("currentMonth"));
        assertEquals(2012, model.getModel().get("currentYear"));
    }

    @Test
    public void testRenderCalendarYearPlus() {
        MockResourceResponse response = new MockResourceResponse();
        ResourceRequest request = mock(ResourceRequest.class);

        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
        when(mockThemeDisplay.getScopeGroupId()).thenReturn(GROUP_ID);
        ModelAndView model = null;
        Calendar cal = Calendar.getInstance();
        DateTime dt = new DateTime();
        dt = dt.plus(Period.years(1));
        try {
            model = calendarController.renderCalendar(response, request, -1, -1, "nextYear");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assertEquals(dt.getYear(), model.getModel().get("currentYear"));
    }
}