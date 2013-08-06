package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 06.08.13
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
public class NewsInMyComm extends State {

    public NewsInMyComm(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        return helper.getNewsService().getPagesCountByOrgAuthor(helper.getUserName(), false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        ModelAndView model = helper.getModel();
        Integer pageCount = helper.getNewsService().getPagesCountByOrgAuthor(helper.getUserName(), false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<News> newsList = helper.getNewsService().getPagesNewsByOrgAuthor(helper.getUserName(), false,
                newCurrentPage, PER_PAGE);
        model.addObject("newsList", newsList);
        model.addObject(TYPE, "News");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }

    @Override
    public void Approve() {
        News currentNews = helper.getNewsService().getNewsById(helper.getObjectId());
        String comment = helper.getComment();
        if (comment.length() > 0) {
            currentNews.setComment(comment);
        }
        currentNews.setOrgApproved(helper.getApproved());
        helper.getNewsService().updateNews(currentNews);
    }
}
