package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 06.08.13
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class MyCommunity extends State {
    public MyCommunity(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        return helper.getOrganizationService().getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        ModelAndView model = helper.getModel();
        Integer pageCount = helper.getOrganizationService().getPagesCountByAuthor(helper.getUserName(), PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Organization> orgList = helper.getOrganizationService().getPagesOrganizationByAuthor(helper.getUserName(),
                newCurrentPage, PER_PAGE);
        model.addObject("orgList", orgList);
        model.addObject(TYPE, "Organization");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
