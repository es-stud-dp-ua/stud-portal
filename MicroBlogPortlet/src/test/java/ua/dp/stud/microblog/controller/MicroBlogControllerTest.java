package ua.dp.stud.microblog.controller;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.dto.MicroBlogDto;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LayoutLocalServiceUtil.class)
public class MicroBlogControllerTest {
    private MicroBlogController controller = new MicroBlogController();
    NewsService mockService;
    Collection<News> news;
    Collection<MicroBlogDto> microBlogDto;

    private final String PORTLET_ID = "NewsArchive_WAR_studnewsArchive";
    private final long GROUP_ID = 1;
    private final long PAGE_ID = 2;

    @Before
    public void setUp() {
        mockService = mock(NewsService.class);

        news = new LinkedList<News>(Collections.nCopies(10, new News()));
        when(mockService.getNewsOnMainPage()).thenReturn(news);
        controller.setNewsService(mockService);
        microBlogDto = mockService.getDtoMicroBlog(news);
        PowerMockito.mockStatic(LayoutLocalServiceUtil.class);
        try {
            PowerMockito.when(LayoutLocalServiceUtil.getDefaultPlid(GROUP_ID, false, PORTLET_ID))
                    .thenReturn(PAGE_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testShowView() {
        RenderResponse response = new MockRenderResponse();
        RenderRequest request = mock(RenderRequest.class);
        ThemeDisplay mockThemeDisplay = mock(ThemeDisplay.class);

        when(request.getAttribute(WebKeys.THEME_DISPLAY)).thenReturn(mockThemeDisplay);
        when(mockThemeDisplay.getScopeGroupId()).thenReturn(GROUP_ID);

        ModelAndView model = null;
        try {
            model = controller.showView(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertEquals(microBlogDto, model.getModel().get("microBlogDto"));
        assertEquals(PAGE_ID, model.getModel().get("newsArchivePageID"));
        assertEquals("viewAll", model.getViewName());
    }
}
