package ua.dp.stud.eventPanel.util.states;

import org.springframework.web.portlet.ModelAndView;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.eventPanel.util.EventPanelHelper;

import java.util.Collection;

/**
 * @author Pikus Vladislav
 */
public class AdminEvents  extends State {
    public AdminEvents(EventPanelHelper helper, String cntDesc, String portletName) {
        super(helper, cntDesc, portletName);
    }

    @Override
    public Integer getPagesCount() {
        EventsService service = helper.getEventsService();
        return service.getPagesCount(false, 1);
    }

    @Override
    public ModelAndView getObjectByPage() {
        EventsService service = helper.getEventsService();
        ModelAndView model = helper.getModel();
        Integer pageCount = service.getPagesCount(false, PER_PAGE);
        Integer newCurrentPage = setPage(pageCount);
        Collection<Events> eventsList = service.getEventsOnPage(newCurrentPage, PER_PAGE, false);
        model.addObject("eventsList", eventsList);
        model.addObject(TYPE, "Events");
        model.addObject(PAGE_COUNT, pageCount);
        model.addObject(CURRENT_PAGE, newCurrentPage);
        setPlid();
        return model;
    }

    @Override
    public void Approve() {
        EventsService service = helper.getEventsService();
        Events currentEvent = service.getEventsById(helper.getObjectId());
        String comment = helper.getComment();
        if (comment.length() > 0) {
            currentEvent.setComment(comment);
        }
        currentEvent.setApproved(helper.getApproved());
        service.updateEvents(currentEvent);
    }
}
