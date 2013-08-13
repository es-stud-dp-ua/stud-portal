package ua.dp.stud.eventPanel.util;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.OrganizationService;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class MyCommunity extends State {
    public MyCommunity(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        OrganizationService service = helper.getOrganizationService();
        return service.getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        OrganizationService service = helper.getOrganizationService();
        String userName = helper.getUserName();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCountByAuthor(userName, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Organization> orgList = service.getPagesOrganizationByAuthor(userName, newCurrentPage,
                PER_PAGE);
        model.addObject("orgList", orgList);
        model.addObject(TYPE, "Organization");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
