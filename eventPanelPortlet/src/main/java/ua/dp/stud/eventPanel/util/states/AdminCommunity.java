package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.OrganizationService;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class AdminCommunity extends State {
    public AdminCommunity(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        OrganizationService service = helper.getOrganizationService();
        return service.getPagesCount(false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        OrganizationService service = helper.getOrganizationService();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCount(false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Organization> orgList = service.getOrganizationsOnPage(false, newCurrentPage, PER_PAGE);
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
