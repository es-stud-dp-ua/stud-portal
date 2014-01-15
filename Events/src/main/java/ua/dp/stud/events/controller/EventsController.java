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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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

import ua.dp.stud.StudPortalLib.dto.EventsDto;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Tags;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.StudPortalLib.util.Direction;
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
    private static final String STR_EXEPT = "Exception";
    private static final String STR_DUPLICAT_TOPIC = "duplicating topic";
    private static final Logger LOG = Logger.getLogger(EventsController.class.getName());
    private static final String TYPE = "type";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int EVENTS_BY_PAGE = 10;
    private static final int NEARBY_PAGES = 2;
    private static final int OVERAL_PAGES = 7;
    private static final String EVENT = "event";
    private static final String EVENT_ID = "eventID";
    private static final String EVENT_DATE_END = "EventDateEnd";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String TRUE = "true";
    private static final String ARCHIVE = "archive";
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
        Boolean future=true;
        Direction directionType=Direction.FUTURE;
        Date sortDate=new Date();

        if (request.getParameter("directType")!=null)
            directionType= Direction.valueOf(request.getParameter("directType"));



        if ((request.getParameter(CURRENT_PAGE) != null) && ("next".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage < eventsService.getPagesCount(EVENTS_BY_PAGE)) {
                currentPage += 1;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("prev".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
            if (currentPage > 1) {
                currentPage--;
            }
        } else if ((request.getParameter(CURRENT_PAGE) != null) && ("temp".equals(request.getParameter("direction")))) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else{
            currentPage = 1;
        }
//PAGINATION
        if (request.getParameter(TYPE) != null) {
            type = EventsType.valueOf(request.getParameter(TYPE));
        } else {
            type = null;
        }

        if ((directionType.equals(Direction.DAY))||(directionType.equals(Direction.FUTURE)))
        {
            future=true;
        }
        else
        if ((directionType.equals(Direction.PREVIOS))||(directionType.equals(Direction.ALL)))
        {
            future=false;
        }


       if (("".equals(request.getParameter("startDate")))||(request.getParameter("startDate")!=null))
        {
            sortDate=new Date(Date.parse(request.getParameter("startDate")));
        }
        else
        {   if(("".equals(request.getParameter("sortDate")))||(request.getParameter("sortdate")!=null))
                {
                sortDate=new Date(Date.parse(request.getParameter("sortdate")));
                }
            else
                sortDate=new Date();
        }
        if (type == null) {
            pagesCount = eventsService.getPagesCount(true, EVENTS_BY_PAGE, future);
            events = eventsService.getEventsOnPage(currentPage, EVENTS_BY_PAGE, true,directionType,sortDate);
        } else {
            pagesCount = eventsService.getPagesCountOfType(EVENTS_BY_PAGE, type, true, future);
            events = eventsService.getEventsOfTypeByPage(currentPage, EVENTS_BY_PAGE, type.toString(), true,directionType,sortDate);
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
        Collection<EventsDto> eventsDto= eventsService.getDtoEvents(events);

        model.addObject("archive", future);
        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
        model.addObject("events", eventsDto);
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
        Collection <EventsDto> eventsDto=eventsService.getDtoEvents(events);
        model.addObject("leftPageNumb", leftPageNumb);
        model.addObject("rightPageNumb", rightPageNumb);
        model.addObject("skippedBeginning", skippedBeginning);
        model.addObject("skippedEnding", skippedEnding);
        model.addObject("events", eventsDto);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("pagesCount", pagesCount);
        model.addObject("EventsByPage", EVENTS_BY_PAGE);
        return model;
    }


    @ActionMapping(value = "sort")
    public void getSortedDate(ActionRequest request, ActionResponse response)
    {
       if (request.getParameter("EventSortDate").equals(""))
           response.setRenderParameter("startDate",(new Date().toString()));
       else
            response.setRenderParameter("startDate",request.getParameter("EventSortDate"));
       response.setRenderParameter("directType",request.getParameter("directType"));
    }




    @RenderMapping(params = "eventID")
    public ModelAndView showSelectedEvents(RenderRequest request, RenderResponse response) throws SystemException, PortalException {
        ModelAndView model = new ModelAndView();
        int eventID = Integer.valueOf(request.getParameter("eventID"));
        Events event = eventsService.getEventsById(eventID);
        ImageImpl mImage = event.getMainImage();
        eventsService.incrementViews(event);
        request.getPortletSession().getPortletContext();
        String mainImage = imageService.getPathToLargeImage(mImage, event);
        int currentPage;
        Boolean future;
        model.setView("viewSingle");
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
         if ("true".equals(request.getParameter("archive")) || (request.getParameter("archive")) == null)
         {
            future=true;
        }
         else {
            future = false;
        }

        model.setView("viewSingle");
        model.addObject(ARCHIVE, future);
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject("mainImage", mainImage);
        model.addObject(EVENT, event);
        model.addObject(START_TIME, event.getEventDateStart().getHours() + ":" + event.getEventDateStart().getMinutes());
        if (event.getEventDateEnd() != null) {
            model.addObject(END_TIME, event.getEventDateEnd().getHours() + ":" + event.getEventDateEnd().getMinutes());
        }
        return model;
    }

    @ModelAttribute(EVENT)
    public Events getCommandObject() {
        return new Events();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("mainImage");
    }

    public Boolean updateEventsFields(Events event, CommonsMultipartFile mainImage, String frmRole, String role, Boolean changeImage) {
        event.setAuthor(role);
        event.setApproved(frmRole.equals(ADMINISTRATOR_ROLE));
        try {
            if (mainImage != null && mainImage.getSize() > 0) {
                if (changeImage) {
                    imageService.saveMainImage(mainImage, event);
                }
            }
        }
        catch (IOException ex) {
            LOG.log(Level.FATAL, STR_EXEPT, ex);
            return false;
        }
//success upload message
        return true;
    }

    @InitBinder(EVENT)
    @ActionMapping(value = "addEvents")
    public void addEvent(@ModelAttribute(value = EVENT) @Valid Events event,
                         BindingResult bindingResult,
                         ActionRequest actionRequest,
                         ActionResponse actionResponse, SessionStatus sessionStatus, @RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage)
            throws SystemException, PortalException {
        CommonsMultipartFile croppedImage = null;
        if (!actionRequest.getParameter("t").equals("") || !"".equals(mainImage.getFileItem().getName())) {
            croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                  Integer.parseInt(actionRequest.getParameter("l")),
                                                  Integer.parseInt(actionRequest.getParameter("w")),
                                                  Integer.parseInt(actionRequest.getParameter("h")));
        } else {
            croppedImage = imageService.getDefaultImage(actionRequest.getPortletSession().getPortletContext().getRealPath(File.separator));
        }
        Date dateStart = new Date(Date.parse(actionRequest.getParameter("EventDateStart")));
        if (!"".equals(actionRequest.getParameter(START_TIME))) {
            String time = actionRequest.getParameter(START_TIME);
            dateStart.setHours(Integer.parseInt(time.substring(0, 2)));
            dateStart.setMinutes(Integer.parseInt(time.substring(3, 5)));
        }
        event.setEventDateStart(dateStart);
        if (!"".equals(actionRequest.getParameter(EVENT_DATE_END))) {
            Date dateEnd = new Date(Date.parse(actionRequest.getParameter(EVENT_DATE_END)));
            if (!"".equals(actionRequest.getParameter(END_TIME))) {
                String time = actionRequest.getParameter(END_TIME);
                dateEnd.setHours(Integer.parseInt(time.substring(0, 2)));
                dateEnd.setMinutes(Integer.parseInt(time.substring(3, 5)));
                event.setEventDateEnd(dateEnd);
            }
        }
        HashSet<String> names = new HashSet<String>();
        ArrayList<Tags> tags = new ArrayList<Tags>();
        String tag = actionRequest.getParameter("tags");
        StringTokenizer tokens = new StringTokenizer(tag, ",.; ");
//check the uniqueness of the name
        String role;
        role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();
//try to update fields for new event
        if (eventsService.isUnique(event.getTitle())) {
            if (updateEventsFields(event, croppedImage, role, usRole, true)) {
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
                event.setTags(tags);
                eventsService.addEvents(event, event.getTags());
                actionResponse.setRenderParameter(EVENT_ID, Integer.toString(event.getId()));
                sessionStatus.setComplete();
            }
        } else {
            actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
        }

    }

    @ActionMapping(value = "editEvent")
    public void editEvent(@RequestParam(MAIN_IMAGE) CommonsMultipartFile mainImage,
                          @ModelAttribute(EVENT)
            @Valid Events event,
                          BindingResult bindingResult,
                          ActionRequest actionRequest,
                          ActionResponse actionResponse, SessionStatus sessionStatus)
            throws IOException, SystemException, PortalException {
//getting current news
        int eventID = Integer.valueOf(actionRequest.getParameter("eventId"));
        Events newEvent = eventsService.getEventsById(eventID);
//getting all parameters from form
        CommonsMultipartFile croppedImage = null;
        HashSet<String> names = new HashSet<String>();
        ArrayList<Tags> tags = new ArrayList<Tags>();
        Boolean defImage = Boolean.valueOf(actionRequest.getParameter("defaultImage"));
        Boolean changeImage = true;

        if (!defImage) {
            if (!actionRequest.getParameter("t").equals("")) {
                croppedImage = imageService.cropImage(mainImage, Integer.parseInt(actionRequest.getParameter("t")),
                                                      Integer.parseInt(actionRequest.getParameter("l")),
                                                      Integer.parseInt(actionRequest.getParameter("w")),
                                                      Integer.parseInt(actionRequest.getParameter("h")));
            } else {
                changeImage = false;
            }
        } else {
            croppedImage = imageService.getDefaultImage(actionRequest.getPortletSession().getPortletContext().getRealPath(File.separator));
        }
        Date dateStart = new Date(Date.parse(actionRequest.getParameter("EventDateStart")));
        if (!"".equals(actionRequest.getParameter(START_TIME))) {
            String time = actionRequest.getParameter(START_TIME);
            dateStart.setHours(Integer.parseInt(time.substring(0, 2)));
            dateStart.setMinutes(Integer.parseInt(time.substring(3, 5)));
        }
        newEvent.setEventDateStart(dateStart);
        if (!"".equals(actionRequest.getParameter(EVENT_DATE_END))) {
            Date dateEnd = new Date(Date.parse(actionRequest.getParameter(EVENT_DATE_END)));
            if (!"".equals(actionRequest.getParameter(END_TIME))) {
                String time = actionRequest.getParameter(END_TIME);
                dateEnd.setHours(Integer.parseInt(time.substring(0, 2)));
                dateEnd.setMinutes(Integer.parseInt(time.substring(3, 5)));
                newEvent.setEventDateEnd(dateEnd);
            }
        }

        newEvent.setTitle(actionRequest.getParameter("title"));
        newEvent.setLocation(actionRequest.getParameter("location"));
        newEvent.setText(actionRequest.getParameter("text"));
        newEvent.setType(EventsType.valueOf(actionRequest.getParameter("type")));

        String tag = actionRequest.getParameter("tags");
        StringTokenizer tokens = new StringTokenizer(tag, ",.; ");
        while (tokens.hasMoreTokens()) {
            names.add(tokens.nextToken());
        }
        for (String tagName : names) {
            Tags tempTags = new Tags();
            tempTags.setName(tagName);
            tempTags.addEvent(event);
            tags.add(tempTags);
        }
        newEvent.setTags(tags);

        String role;
        role = actionRequest.isUserInRole(ADMINISTRATOR_ROLE) ? ADMINISTRATOR_ROLE : USER_ROLE;
        User user = (User) actionRequest.getAttribute(WebKeys.USER);
        String usRole = user.getScreenName();

        if (updateEventsFields(newEvent, croppedImage, role, usRole, changeImage)) {
            eventsService.updateEvents(newEvent);
//close session
            sessionStatus.setComplete();
        } else {
            actionResponse.setRenderParameter(STR_FAIL, STR_DUPLICAT_TOPIC);
        }
    }

    @RenderMapping(params = "mode=add")
    public ModelAndView showAddEvent(RenderRequest request, RenderResponse response) {
        ModelAndView model = new ModelAndView();
//set view for add
        Events event=new Events();
        ImageImpl mImage = event.getMainImage();
        String mainImageUrl = imageService.getPathToLargeImage(mImage, event);
        event.setEventDateStart(new Date());
        event.setEventDateEnd(new Date());
        event.setType(EventsType.SPORTS);
        event.setMainImage(null);
        model.setViewName("addEvent");
        model.addObject(EVENT, event);
        model.addObject(MAIN_IMAGE,mainImageUrl);
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
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        Boolean future;
        if (TRUE.equals(request.getParameter(ARCHIVE)) || (request.getParameter(ARCHIVE)) == null) {
            future = true;
        } else {
            future = false;
        }
        //set view for edit
        model.setView("editEvent");
//send current event in view
        model.addObject(CURRENT_PAGE, currentPage);
        model.addObject(ARCHIVE, future);
        model.addObject(EVENT, event);
        model.addObject(MAIN_IMAGE, mainImageUrl);
        model.addObject("additionalImages", additionalImages);
        return model;
    }

    @RenderMapping(params = "mode=delete")
    public ModelAndView deleteEvent(RenderRequest request, RenderResponse response) {
//getting current events
        int eventID = Integer.valueOf(request.getParameter(EVENT_ID));
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
        int currentPage;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        } else {
            currentPage = 1;
        }
        Boolean future;
        if (TRUE.equals(request.getParameter(ARCHIVE)) || (request.getParameter(ARCHIVE)) == null) {
            future = true;
        } else {
            future = false;
        }
//send current event in view
        model.addObject(ARCHIVE, future);
        model.addObject(CURRENT_PAGE, currentPage);
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