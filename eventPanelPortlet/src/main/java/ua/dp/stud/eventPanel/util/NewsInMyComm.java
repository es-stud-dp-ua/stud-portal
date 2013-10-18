package ua.dp.stud.eventPanel.util;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class NewsInMyComm extends State {

    public NewsInMyComm(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        NewsService service = helper.getNewsService();
        return service.getPagesCountByOrgAuthor(helper.getUserName(), false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        NewsService service = helper.getNewsService();
        String userName = helper.getUserName();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCountByOrgAuthor(userName, false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<News> newsList = service.getPagesNewsByOrgAuthor(userName, false, newCurrentPage, PER_PAGE);
        model.addObject("newsList", newsList);
        model.addObject(TYPE, "News");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }

    @Override
    public void approve() {
        NewsService service = helper.getNewsService();
        News currentNews = service.getNewsById(helper.getObjectId());
        String comment = helper.getComment();
        if (comment.length() > 0) {
            currentNews.setComment(comment);
        }
        currentNews.setOrgApproved(helper.getApproved());
        service.updateNews(currentNews);
    }
}
