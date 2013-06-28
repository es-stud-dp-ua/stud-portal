package ua.dp.stud.eventPanel.controller;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.NewsService;
import ua.dp.stud.StudPortalLib.service.OrganizationService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
@Controller
@RequestMapping(value = "view")
public class EventPanelController
{
    private static final int PER_PAGE = 4;
    private static final String FIRST_STATE = "first";
    private static final String NEXT_STATE = "next";
    private static final String PREV_STATE = "prev";
    private static final String TYPE = "type";
    private static final String NEWS_ARCHIVE_REFERENCE_NAME = "NewsArchive_WAR_studnewsArchive";
    private static final String COMMUNITIES_REFERENCE_NAME = "Communities_WAR_studcommunity";

    private String userName;

    @Autowired
    @Qualifier(value = "OrganizationService")
    private OrganizationService orgService;
    public void setOrgService(OrganizationService service)
    {
        this.orgService = service;
    }

    @Autowired
    @Qualifier(value = "newsService")
    private NewsService newsService;
    public void setNewsService(NewsService service) {
        this.newsService = service;
    }

    /**
     * Show event panel
     * @param request
     * @param response
     * @return
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response)
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("view");

        userName = getCurrentUserName(request);
        Integer myNewsSize = newsService.getPagesCountByAuthor(userName, 1);
        Integer myOrgSize = orgService.getPagesCountByAuthor(userName, 1);
        Integer adminNewsSize = newsService.getPagesCount(false, 1);
        Integer adminOrgSize = orgService.getPagesCount(false, 1);
        Integer newsInMyComm = newsService.getPagesCountByOrgAuthor(userName, false, 1);

        model.addObject("myNewsSize", myNewsSize);
        model.addObject("myOrgSize", myOrgSize);
        model.addObject("adminOrgSize", adminOrgSize);
        model.addObject("adminNewsSize", adminNewsSize);
        model.addObject("newsInMyComm", newsInMyComm);

        return model;
    }

    /**
     * Show community for approve
     * @param request
     * @param response
     * @param currentPage current page
     * @param direction takes one of three values​​: the first, prev, next
     * @return
     */
    @RenderMapping(params = "mode=pagination")
    public ModelAndView pagination(RenderRequest request, RenderResponse response,
                                @RequestParam(required = true, defaultValue = "1") int currentPage,
                                @RequestParam(required = true, defaultValue = FIRST_STATE) String direction,
                                @RequestParam(required = true, defaultValue = "view") String modelView)
    {
        ModelAndView model = showView(request, response);
        Integer pageCount;
        Integer newCurrentPage;
        Collection<Organization> orgList;
        Collection<News> newsList;
        //todo: instead of if-else use map
        if (modelView.equals("myNews"))
        {
            pageCount = newsService.getPagesCountByAuthor(userName, PER_PAGE);
            newCurrentPage = setPage(currentPage, pageCount, direction);
            newsList = newsService.getPagesNewsByAuthor(userName, newCurrentPage, PER_PAGE);
            setPlid(request, model, NEWS_ARCHIVE_REFERENCE_NAME);
            model.addObject("newsList", newsList);
            model.addObject(TYPE, "News");
        }
        else
        {
            if (modelView.equals("myCommunity"))
            {
                pageCount = orgService.getPagesCountByAuthor(userName, PER_PAGE);
                newCurrentPage = setPage(currentPage, pageCount, direction);
                orgList = orgService.getPagesOrganizationByAuthor(userName, newCurrentPage, PER_PAGE);
                setPlid(request, model, COMMUNITIES_REFERENCE_NAME);
                model.addObject("orgList", orgList);
                model.addObject(TYPE, "Organization");
            }
            else
            {
                if (modelView.equals("newsInMyComm"))
                {
                    pageCount = newsService.getPagesCountByOrgAuthor(userName,false, PER_PAGE);
                    newCurrentPage = setPage(currentPage, pageCount, direction);
                    newsList = newsService.getPagesNewsByOrgAuthor(userName, false, newCurrentPage, PER_PAGE);
                    setPlid(request, model, NEWS_ARCHIVE_REFERENCE_NAME);
                    model.addObject("newsList", newsList);
                    model.addObject(TYPE, "News");
                }
                else
                {
                    if (modelView.equals("adminNews"))
                    {
                        pageCount = newsService.getPagesCount(false, PER_PAGE);
                        newCurrentPage = setPage(currentPage, pageCount, direction);
                        newsList = newsService.getNewsOnPage(false, newCurrentPage, PER_PAGE);
                        setPlid(request, model, NEWS_ARCHIVE_REFERENCE_NAME);
                        model.addObject("newsList", newsList);
                        model.addObject(TYPE, "News");
                    }
                    else
                    {
                        if (modelView.equals("adminCommunity"))
                        {
                            pageCount = orgService.getPagesCount(false, PER_PAGE);
                            newCurrentPage = setPage(currentPage, pageCount, direction);
                            orgList = orgService.getOrganizationsOnPage(false, newCurrentPage, PER_PAGE);
                            setPlid(request, model, COMMUNITIES_REFERENCE_NAME);
                            model.addObject("orgList", orgList);
                            model.addObject(TYPE, "Organization");
                        }
                        else
                        {
                            return model;
                        }
                    }
                }
            }
        }
        model.addObject("pageCount", pageCount);
        model.addObject("currentPage", newCurrentPage);
        model.addObject("class", modelView);
        return model;
    }

    /**
     * Set approve for news or organization
     * @param request
     * @param response
     * @param currentPage current page
     * @param direction takes one of three values​​: the first, prev, next or current
     * @param modelView name of view
     * @param objectId id of news or organization
     * @return
     */
    @RenderMapping(params = "mode=approve")
    public ModelAndView approve(RenderRequest request, RenderResponse response,
                                @RequestParam(required = true, defaultValue = "1") int currentPage,
                                @RequestParam(required = true, defaultValue = "current") String direction,
                                @RequestParam(required = true, defaultValue = "view") String modelView,
                                @RequestParam(required = true, defaultValue = "0") int objectId,
                                @RequestParam(required = true, defaultValue = "false") boolean appr)
    {
        ModelAndView model;
        if (modelView.equals("newsInMyComm"))
        {
            News currentNews = newsService.getNewsById(objectId);
            currentNews.setOrgApproved(true);
            newsService.updateNews(currentNews);
        }
        else
        {
            if (modelView.equals("adminNews"))
            {
                News currentNews = newsService.getNewsById(objectId);
                currentNews.setApproved(true);
                newsService.updateNews(currentNews);
            }
            else
            {
                if (modelView.equals("adminCommunity"))
                {
                    Organization currentOrg = orgService.getOrganizationById(objectId);
                    currentOrg.setApproved(true);
                    orgService.updateOrganization(currentOrg);
                }
                else
                {
                    model = showView(request, response);
                    return model;
                }
            }
        }
        model = pagination(request, response, currentPage, direction, modelView);
        return model;
    }

    /**
     * Set current page
     * @param currentPage current page
     * @param pageCount page count
     * @param direction takes one of three values​​: the first, prev, nex
     * @return current page
     */
    private int setPage(Integer currentPage, Integer pageCount, String direction)
    {
        Integer newCurrentPage = currentPage;
        if (direction.equals(NEXT_STATE))
        {
            newCurrentPage++;
        }
        else
        {
            if (direction.equals(PREV_STATE))
            {
                newCurrentPage--;
            }
            else
            {
                if (direction.equals(FIRST_STATE))
                {
                    newCurrentPage = 1;
                }
            }
        }
        if (pageCount < newCurrentPage)
        {
            newCurrentPage = 1;
        }
        else
        {
            if (newCurrentPage < 1)
            {
                newCurrentPage = pageCount;
            }
        }
        return newCurrentPage;
    }

    /**
     * Get current user name
     * @param request
     * @return return user name
     */
    private String getCurrentUserName(RenderRequest request)
    {
        User user = (User) request.getAttribute(WebKeys.USER);
        return (user != null) ? user.getScreenName() : "";
    }

    /**
     *
     * @param request
     * @param model
     * @param portletName portlet name
     */
    private void setPlid(RenderRequest request, ModelAndView model, String portletName)  {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        Long groupId = themeDisplay.getScopeGroupId();
        Long plid = 0l;
        try{
            plid = LayoutLocalServiceUtil.getDefaultPlid(groupId, false, portletName);
        }   catch (Exception ex){

        }
        model.addObject("plid", plid);
        model.addObject("portlet_name", portletName);
    }
}
