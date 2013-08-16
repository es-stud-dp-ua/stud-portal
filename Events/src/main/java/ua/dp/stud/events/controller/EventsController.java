/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.events.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;
import org.joda.time.DateTime;
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
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Tags;
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
    private static final String STR_FAIL = "fail";
    private static final String STR_NO_IMAGE = "no images";
    private static final String STR_BAD_IMAGE = "Failed to load image";
    private static final String STR_EXEPT = "Exception";
    private static final String STR_DUPLICAT_TOPIC = "duplicating topic";
    private static final Logger LOG = Logger.getLogger(EventsController.class.getName());
    private static final String TYPE = "type";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int EVENTS_BY_PAGE = 5;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERAL_PAGES = 7;
    @Autowired
    @Qualifier(value = "eventsService")
    private EventsService eventsService;

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }
    @Autowired
    @Qualifier(value = "imageService")
    private ImageService imageService;

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @RenderMapping
    public ModelAndView showView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewAll");

        Collection<Events> events;

        int pagesCount;
        int currentPage;
        EventsType type;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
//PAGINATION 

        if (request.getParameter(TYPE) != null) {
            type = EventsType.valueOf(request.getParameter(TYPE));
        } else {
            type = null;
        }
        if (type == null) {
            pagesCount = eventsService.getPagesCount(EVENTS_BY_PAGE);
            events = eventsService.getEventsOnPage(currentPage, EVENTS_BY_PAGE, true);
        } else {
            pagesCount = eventsService.getPagesCountOfType(EVENTS_BY_PAGE, type);
            events = eventsService.getEventsOfTypeByPage(currentPage, EVENTS_BY_PAGE, type.toString(), true);
            System.out.println(events);
            System.out.println(type.toString());
            System.out.println(currentPage);
        }

        int leftPageNumb = Math.max(1, currentPage - NEARBY_PAGES);
        int rightPageNumb = Math.min(pagesCount, currentPage + NEARBY_PAGES);
        boolean skippedBeginning = false;
        boolean skippedEnding = false;

        if (pagesCount <= OVERAL_PAGES) {
            leftPageNumb = 1;
            rightPageNumb = pagesCount;
        } else {
            if (currentPage > 2 + NEARBY_PAGES) {
                skippedBeginning = true;
            } else {
                leftPageNumb = 1;
                rightPageNumb = 2 + 2 * NEARBY_PAGES;
            }
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                skippedEnding = true;
            } else {
                leftPageNumb = (pagesCount - 1) - 2 * NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }

        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
        model.addObject("events", events);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject(TYPE, type);
        model.addObject("EventsByPage", EVENTS_BY_PAGE);
        return model;
    }

    @RenderMapping(params = "tagID")
    public ModelAndView showTagView(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("viewAll");
        int tagID = Integer.valueOf(request.getParameter("tagID"));
        Tags tag = eventsService.getTagById(tagID);

        List<Events> events = tag.getEvents();
        int pagesCount;
        int currentPage;

        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        pagesCount = (events.size() > 0) ? ((events.size() - 1) / EVENTS_BY_PAGE + 1) : 0;
        int leftPageNumb = Math.max(1, currentPage - NEARBY_PAGES);
        int rightPageNumb = Math.min(pagesCount, currentPage + NEARBY_PAGES);
        boolean skippedBeginning = false;
        boolean skippedEnding = false;
        if (pagesCount <= OVERAL_PAGES) {
            leftPageNumb = 1;
            rightPageNumb = pagesCount;
        } else {
            if (currentPage > 2 + NEARBY_PAGES) {
                skippedBeginning = true;
            } else {
                leftPageNumb = 1;
                rightPageNumb = 2 + 2 * NEARBY_PAGES;
            }
            if (currentPage < pagesCount - (NEARBY_PAGES + 1)) {
                skippedEnding = true;
            } else {
                leftPageNumb = (pagesCount - 1) - 2 * NEARBY_PAGES;
                rightPageNumb = pagesCount;
            }
        }

        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
        model.addObject("events", events);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("EventsByPage", EVENTS_BY_PAGE);
        return model;
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination")
    public void showPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        }
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=next")
    public void showNextPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (request.getParameter(TYPE) == null) {
            if (currentPage < eventsService.getPagesCount(EVENTS_BY_PAGE)) {
                currentPage += 1;
            }
        } else if (currentPage < eventsService.getPagesCountOfType(EVENTS_BY_PAGE, EventsType.valueOf(request.getParameter(TYPE)))) {
            currentPage += 1;
        }
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        }
    }

    /**
     * Pagination handling
     *
     * @param request
     * @param response
     */
    @ActionMapping(value = "pagination", params = "direction=prev")
    public void showPrevPage(ActionRequest request, ActionResponse response) {
        int currentPage = Integer.valueOf(request.getParameter("pageNumber"));
        if (currentPage > 1) {
            currentPage -= 1;
        }
        response.setRenderParameter(CURRENT_PAGE, String.valueOf(currentPage));
        if (request.getParameter(TYPE) != null) {
            response.setRenderParameter(TYPE, request.getParameter(TYPE));
        }
    }

    @RenderMapping(params = "eventID")
    public ModelAndView showSelectedEvents(RenderRequest request, RenderResponse response) throws SystemException, PortalException {
        ModelAndView model = new ModelAndView();
        int eventID = Integer.valueOf(request.getParameter("eventID"));
        Events event = eventsService.getEventsById(eventID);
        ImageImpl mImage = event.getMainImage();
        eventsService.incrementViews(event);
        String mainImage = imageService.getPathToLargeImage(mImage, event);
        System.out.println(event.getTags());
        model.setView("viewSingle");
        model.addObject("mainImage", mainImage);
        model.addObject("event", event);
        model.addObject("startTime", event.getEventDateStart().getHours() + ":" + event.getEventDateStart().getMinutes());
        if (event.getEventDateEnd() != null) {
            model.addObject("endTime", event.getEventDateEnd().getHours() + ":" + event.getEventDateEnd().getMinutes());
        }
        return model;
    }

    @ModelAttribute("event")
    public Events getCommandObject() {
        return new Events();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");
    }

    public Boolean updateEventsFields(Events event, CommonsMultipartFile mainImage, CommonsMultipartFile[] images, String frmRole, String role) {
        event.setAuthor(role);
        if (frmRole.equals(ADMINISTRATOR_ROLE)) {
            event.setApproved(true);
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

    @InitBinder("event")
    @ActionMapping(value = "addEvents")
    public void addEvent(@ModelAttribute(value = "event") @Valid Events event,
            BindingResult bindingResult,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus, @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images)
            throws SystemException, PortalException {
//        if (bindingResult.hasFieldErrors()) {
//            actionResponse.setRenderParameter(STR_FAIL, " ");
//        } else {
//path for main image is not empty
        if (mainImage.getOriginalFilename().equals("")) {
            actionResponse.setRenderParameter(STR_FAIL, STR_NO_IMAGE);
        }
//getting all parameters from form
//            EventsType type = EventsType.valueOf(actionRequest.getParameter("type"));
//crop main image
        CommonsMultipartFile croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                Integer.parseInt(actionRequest.getParameter("l")),
                Integer.parseInt(actionRequest.getParameter("w")),
                Integer.parseInt(actionRequest.getParameter("h")));
        Date dateStart = new Date(Date.parse(actionRequest.getParameter("EventDateStart")));
        if (!"".equals(actionRequest.getParameter("startTime"))) {
            String time = actionRequest.getParameter("startTime");
            dateStart.setHours(Integer.parseInt(time.substring(0, 2)));
            dateStart.setMinutes(Integer.parseInt(time.substring(3, 5)));
        }
        event.setEventDateStart(dateStart);
        if (!"".equals(actionRequest.getParameter("EventDateEnd"))) {
            Date dateEnd = new Date(Date.parse(actionRequest.getParameter("EventDateEnd")));
            if (!"".equals(actionRequest.getParameter("endTime"))) {
                String time = actionRequest.getParameter("endTime");
                dateEnd.setHours(Integer.parseInt(time.substring(0, 2)));
                dateEnd.setMinutes(Integer.parseInt(time.substring(3, 5)));
                event.setEventDateEnd(dateEnd);
            }
        }
        HashSet<String> names = new HashSet<String>();
        ArrayList<Tags> tags = new ArrayList<Tags>();
        String tag = actionRequest.getParameter("tags");
        StringTokenizer tokens = new StringTokenizer(tag, ",.; ");

        if (croppedImage == null) {
            actionResponse.setRenderParameter(STR_FAIL, STR_BAD_IMAGE);
            return;
        }
//check the uniqueness of the name
        String role;
        role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
//try to update fields for new event
        if (eventsService.isUnique(event.getTitle())) {
            if (updateEventsFields(event, croppedImage, images, role, usRole)) {
                Date date = new Date();
                event.setPublication(date);
                while (tokens.hasMoreTokens()) {
                    names.add(tokens.nextToken());
                }
                for (String tagName : names) {
                    Tags tempTags = new Tags();
                    tempTags.setName(tagName);
                    tempTags.addEvent(event);
                    tags.add(tempTags);
                }
                System.out.println(tags);
                event.setTags(tags);
                eventsService.addEvents(event, event.getTags());
                actionResponse.setRenderParameter("eventID", Integer.toString(event.getId()));
                sessionStatus.setComplete();
            }
        } else {
            actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
        }

//        }
    }

    @ActionMapping(value = "editEvent")
    public void editEvent(@RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
            @RequestParam("images") CommonsMultipartFile[] images,
            @ModelAttribute(value = "event")
            @Valid Events event,
            BindingResult bindingResult,
            ActionRequest actionRequest,
            ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
//getting current news
        int eventID = Integer.valueOf(actionRequest.getParameter("eventID"));
        Events newEvent = eventsService.getEventsById(eventID);
//getting all parameters from form
//        if (bindingResult.hasFieldErrors()) {
//            actionResponse.setRenderParameter(STR_FAIL, " ");
//        } else {
        CommonsMultipartFile croppedImage;
        if (!actionRequest.getParameter("t").equals("")) {
            croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                    Integer.parseInt(actionRequest.getParameter("l")),
                    Integer.parseInt(actionRequest.getParameter("w")),
                    Integer.parseInt(actionRequest.getParameter("h")));
        } else {
            croppedImage = mainImage;
        }
        if (croppedImage == null) {
            actionResponse.setRenderParameter(STR_FAIL, STR_BAD_IMAGE);
            return;
        }
        Date dateStart = new Date(Date.parse(actionRequest.getParameter("EventDateStart")));
        if (!"".equals(actionRequest.getParameter("startTime"))) {
            String time = actionRequest.getParameter("startTime");
            dateStart.setHours(Integer.parseInt(time.substring(0, 2)));
            dateStart.setMinutes(Integer.parseInt(time.substring(3, 5)));
        }
        newEvent.setEventDateStart(dateStart);
        if (!"".equals(actionRequest.getParameter("EventDateEnd"))) {
            Date dateEnd = new Date(Date.parse(actionRequest.getParameter("EventDateEnd")));
            if (!"".equals(actionRequest.getParameter("endTime"))) {
                String time = actionRequest.getParameter("endTime");
                dateEnd.setHours(Integer.parseInt(time.substring(0, 2)));
                dateEnd.setMinutes(Integer.parseInt(time.substring(3, 5)));
                newEvent.setEventDateEnd(dateEnd);
            }
        }
        String role;
        role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
        if (updateEventsFields(newEvent, mainImage, images, role, usRole)) {
            eventsService.updateEvents(newEvent);
//close session
            sessionStatus.setComplete();
        } else {
            actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
        }
//        }
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddEvent(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
//set view for add
        model.setViewName("addEvent");
        return model;
    }

    @RenderMapping(params = "mode=delImage")
    public ModelAndView delImage(RenderRequest request, RenderResponse response) {
        long imageID = Long.valueOf(request.getParameter("imageId"));
        ImageImpl image = eventsService.getImageById(imageID);
//delete image from folder
        imageService.deleteImage(image, image.getBase());
//delete image from data base
        eventsService.deleteImage(image);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "mode=edit")
    public ModelAndView showEditEvent(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
//getting event
        int eventID = Integer.valueOf(request.getParameter("eventId"));
        Events event = eventsService.getEventsById(eventID);
        ImageImpl mImage = event.getMainImage();
        String mainImageUrl = imageService.getPathToLargeImage(mImage, event);
        Collection<ImageImpl> additionalImages = event.getAdditionalImages();
//set view for edit
        model.setView("editEvent");
//send current event in view
        model.addObject("event", event);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteEvent(RenderRequest request, RenderResponse response) {
//getting current events
        int eventID = Integer.valueOf(request.getParameter("eventID"));
        Events event = eventsService.getEventsById(eventID);
//delete chosen organization's image from folder
        imageService.deleteDirectory(event);
//delete chosen news
        eventsService.deleteEvents(event);
        return showAddSuccess(request, response);
    }

    @RenderMapping(params = "success")
    public ModelAndView showAddSuccess(RenderRequest request, RenderResponse response) {
        ModelAndView model = showView(request, response);
        String strSuccess = "success";
        SessionMessages.add(request, request.getParameter(strSuccess));
        return model;
    }

    @RenderMapping(params = "fail")
    public ModelAndView showAddFailed(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddEvent(request, response);
        model.addObject(STR_EXEPT, request.getParameter(STR_FAIL));
        return model;
    }

    @RenderMapping(params = "exception")
    public ModelAndView showAddException(RenderRequest request, RenderResponse response) {
        ModelAndView model = showAddEvent(request, response);
        model.addObject(STR_EXEPT, request.getParameter(STR_EXEPT));
        return model;
    }
}