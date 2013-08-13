package ua.dp.stud.eventPanel.util;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.service.EventsService;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class MyEvents extends State {

    public MyEvents(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        EventsService service = helper.getEventsService();
        return service.getPagesCountByAuthor(helper.getUserName(), 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        String userName = helper.getUserName();
        EventsService service = helper.getEventsService();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCountByAuthor(userName, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Events> eventsList = service.getPagesEventsByAuthor(userName, newCurrentPage, PER_PAGE);
        model.addObject("eventsList", eventsList);
        model.addObject(TYPE, "Events");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }
}
