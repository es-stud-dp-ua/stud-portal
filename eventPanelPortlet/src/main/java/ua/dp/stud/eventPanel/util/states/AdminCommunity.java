package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 06.08.13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class AdminCommunity extends State {
    public AdminCommunity(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        return helper.getOrganizationService().getPagesCount(false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        ModelAndView model = helper.getModel();
        Integer pageCount = helper.getOrganizationService().getPagesCount(false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Organization> orgList = helper.getOrganizationService().getOrganizationsOnPage(false, newCurrentPage,
                PER_PAGE);
        model.addObject("orgList", orgList);
        model.addObject(TYPE, "Organization");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }

    @Override
    public void Approve() {
        Organization currentOrg = helper.getOrganizationService().getOrganizationById(helper.getObjectId());
        String comment = helper.getComment();
        if (comment.length() > 0) {
            currentOrg.setComment(comment);
        }
        currentOrg.setApproved(helper.getApproved());
        helper.getOrganizationService().updateOrganization(currentOrg);
    }
}
