package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.service.NewsService;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class MyNews extends State {

    public MyNews(EventPanelHelper helper, String cntDesc, String portletName) {
       super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        NewsService service = helper.getNewsService();
        return service.getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        NewsService service = helper.getNewsService();
        String userName = helper.getUserName();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCountByAuthor(userName, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<News> newsList = service.getPagesNewsByAuthor(userName, newCurrentPage, PER_PAGE);
        model.addObject("newsList", newsList);
        model.addObject(TYPE, "News");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
