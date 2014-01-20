package ua.dp.stud.microblog.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.dto.MicroBlogDto;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Controller for view mode
 */
@Controller
@RequestMapping(value = "view")
public class MicroBlogController {
    private static final String NEWS_ARCHIVE_REFERENCE_NAME = "NewsArchive_WAR_studnewsArchive";
    private static final Logger LOGGER = Logger.getLogger(MicroBlogController.class.getName());

    @Autowired
    @Qualifier(value = "newsService")
    private NewsService newsService;

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Method for rendering view mode
     *
     * @param request
     * @param response
     * @return
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response)
            throws PortalException, SystemException {
        return getMainView(request);
    }

    private ModelAndView getMainView(RenderRequest request)
            throws PortalException, SystemException {
        ModelAndView model = new ModelAndView("viewAll");

        Collection<News> news = newsService.getNewsOnMainPage();
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getScopeGroupId();
        Long plid = LayoutLocalServiceUtil.getDefaultPlid(groupId, false, NEWS_ARCHIVE_REFERENCE_NAME);
        Collection<MicroBlogDto> microBlogDto = newsService.getDtoMicroBlog(news);
        model.addObject("microBlogDto", microBlogDto);
        model.addObject("newsArchivePageID", plid);
        return model;
    }

    @RenderMapping(params = "mode=remove")
    public ModelAndView remove(RenderRequest request, RenderResponse response)
            throws PortalException, SystemException  {
        Integer newsId = Integer.valueOf(request.getParameter("newsID"));
        News news = newsService.getNewsById(newsId);
        news.setOnMainpage(false);
        newsService.updateNews(news);
        return getMainView(request);
    }
}

