/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.events.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Organization;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.StudPortalLib.util.EventsType;
import ua.dp.stud.StudPortalLib.util.ImageService;

/**
 *
 * @author Ольга
 */
@Controller
@RequestMapping(value = "view")
public class EventsController {

    private static final String MAIN_IMAGE = "mainImage";
    private static final String ADMINISTRATOR_ROLE = "Administrator";
    private static final String USER_ROLE = "User";
    private static final String STR_EXEPT = "Exception";
    
    
    private static final Logger LOG = Logger.getLogger(EventsController.class.getName());
    @Autowired
    @Qualifier(value = "eventsService")
    private EventsService eventsService;
    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    @RenderMapping(params = "eventId")
    public ModelAndView showSelectedEvents(RenderRequest request, RenderResponse response) throws SystemException, PortalException {
        ModelAndView model = new ModelAndView();
        int eventsID = Integer.valueOf(request.getParameter("eventsID"));
        Events event = eventsService.getEventsById(eventsID);
        ImageImpl mImage = event.getMainImage();
        String mainImageUrl = imageService.getPathToLargeImage(mImage, event);
        Collection<ImageImpl> additionalImages = event.getAdditionalImages();
        model.setView("viewSingle");
        model.addObject("additionalImages", additionalImages);
        model.addObject("mainImageUrl", mainImageUrl);
        model.addObject("event", event);
        return model;
    }

    @ModelAttribute("events")
    public Events getCommandObject() {
        return new Events();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");
    }

    public Boolean updateEventsFields(Events event, String title, String text, String location, Date eventDate, Date eventTime, EventsType type,
            CommonsMultipartFile mainImage, CommonsMultipartFile[] images, String frmRole, String role) {
        event.setTitle(title);
        event.setText(text);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setType(type);
        event.setAuthor(role);
        if (frmRole.equals(ADMINISTRATOR_ROLE)) {
            event.setApprove(true);
        }
        try {
            if (mainImage.getSize() > 0) {
                imageService.saveMainImage(mainImage, event);
            }
            if (images != null && images.length > 0) {
                for (CommonsMultipartFile file : images) {
                    imageService.saveAdditionalImages(file, event);
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, STR_EXEPT, ex);
            return false;
        }
//success upload message
        return true;
    }

    @InitBinder("events")
    @ActionMapping(value = "addEvents")
    public void addEvent(@ModelAttribute(value = "events") @Valid Events event,
            BindingResult bindingResult,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus, @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images)
            throws SystemException, PortalException {
    }
}