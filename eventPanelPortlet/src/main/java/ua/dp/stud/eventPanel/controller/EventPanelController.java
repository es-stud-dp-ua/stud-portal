package ua.dp.stud.eventPanel.controller;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.eventPanel.util.EventPanelHelper;
import ua.dp.stud.eventPanel.util.states.State;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pikus Vladislav
 */
@Controller
@RequestMapping(value = "view")
public class EventPanelController {
    private static final String VIEW = "view";

    @Autowired
    @Qualifier(value = "eventPanelHelper")
    private EventPanelHelper helper;

    public void setHelper(EventPanelHelper helper) {
        this.helper = helper;
    }

    /**
     * Create current model and set new user name
     *
     * @param request
     * @return new model
     */
    private ModelAndView getCurrentModel(RenderRequest request) {
        helper.setUserName(getCurrentUserName(request));
        helper.setModel(new ModelAndView(VIEW));
        return helper.getPagesCount();
    }

    /**
     * Show event panel
     *
     * @param request
     * @param response
     * @return
     */
    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        return getCurrentModel(request);
    }

    /**
     * Show community for approve
     *
     * @param request
     * @param response
     * @param currentPage current page
     * @param direction   takes one of three values​​: the first, prev, next
     * @return
     */
    @RenderMapping(params = "mode=pagination")
    public ModelAndView pagination(RenderRequest request, RenderResponse response,
                                   @RequestParam(required = true, defaultValue = "1") int currentPage,
                                   @RequestParam(required = true, defaultValue = State.FIRST_STATE) String direction,
                                   @RequestParam(required = true, defaultValue = VIEW) String modelView) {
        getCurrentModel(request);
        helper.setState(modelView);
        helper.setDirection(direction);
        helper.setRequest(request);
        helper.setCurrentPage(currentPage);
        ModelAndView model = helper.getObjectsByPage();
        model.addObject("class", modelView);
        return model;
    }

    /**
     * Set approve for news or organization
     *
     * @param request
     * @param response
     * @param currentPage current page
     * @param direction   takes one of three values​​: the first, prev, next or
     *                    current
     * @param modelView   name of view
     * @param objectId    id of news or organization
     * @return
     */
    @RenderMapping(params = "mode=approve")
    public ModelAndView approve(RenderRequest request, RenderResponse response,
                                @RequestParam(required = true, defaultValue = "1") int currentPage,
                                @RequestParam(required = true, defaultValue = "current") String direction,
                                @RequestParam(required = true, defaultValue = VIEW) String modelView,
                                @RequestParam(required = true, defaultValue = "0") int objectId,
                                @RequestParam(required = true, defaultValue = "false") boolean appr,
                                @RequestParam(required = true, defaultValue = "") String comment) {
        helper.setState(modelView);
        helper.setObjectId(objectId);
        helper.setComment(comment);
        helper.setApproved(appr);
        helper.Approve();
        return pagination(request, response, currentPage, direction, modelView);
    }

    /**
     * Get current user name
     *
     * @param request
     * @return return user name
     */
    private String getCurrentUserName(RenderRequest request) {
        User user = (User) request.getAttribute(WebKeys.USER);
        return (user != null) ? user.getScreenName() : "";
    }
}
