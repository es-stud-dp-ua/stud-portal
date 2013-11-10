/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Tags;
import ua.dp.stud.StudPortalLib.util.Direction;
import ua.dp.stud.StudPortalLib.util.EventsType;

/**
 *
 * @author Ольга
 */
public interface EventsService {

    Collection<Events> getEventsByType(String type);

    ImageImpl getImageById(Long id);

    void deleteImage(ImageImpl image);

    Integer getPagesCount(Integer newsByPage);

    Collection<Events> getEventsOnPage(Integer pageNumb, Integer eventsByPage, Boolean approve,Direction direct,Date date);

    Collection<Events> getAllEvents(Boolean approve);

    Events getEventsById(Integer id);

    void addImage(ImageImpl image);

    void deleteEvents(Events events);

    void updateEvents(Events events);

    void addEvents(Events eventsToAdd);
    
    void addEvents(Events eventsToAdd,List<Tags> tags) ;

    Boolean isUnique(String name);

    Collection<Events> getOnMainPage();

    Collection<Events> getEventsOfTypeByPage(Integer pageNumb, Integer eventsByPage, String type, Boolean approve,Direction direct,Date date);

    Integer getPagesCountOfType(int eByPage, EventsType type,Boolean approved,Boolean futureEvents);

    void incrementViews(Events organization);

    Integer getPagesCountByAuthor(String author, Integer eventsPerPage);

    Collection<Events> getPagesEventsByAuthor(String author, Integer pageNumb, Integer eventsPerPage);

    Integer getPagesCount(Boolean approved, Integer newsPerPage,Boolean futureEvents);
    
    //** TAGS */
    Tags getTagById(Integer id);
    
    void setTag(Tags tag);
    
    Collection<Tags> getAllTags();
    
    void deleteTags(Tags tags);
    
    void updateTags(Tags tag);


}
