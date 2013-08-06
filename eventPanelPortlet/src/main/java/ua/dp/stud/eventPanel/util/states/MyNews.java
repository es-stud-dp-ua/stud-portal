package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 06.08.13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
public class MyNews extends State {

    public MyNews(EventPanelHelper helper, String cntDesc, String portletName) {
       super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        return helper.getNewsService().getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        ModelAndView model = helper.getModel();
        Integer pageCount = helper.getNewsService().getPagesCountByAuthor(helper.getUserName(), PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<News> newsList = helper.getNewsService().getPagesNewsByAuthor(helper.getUserName(), newCurrentPage, PER_PAGE);
        model.addObject("newsList", newsList);
        model.addObject(TYPE, "News");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
