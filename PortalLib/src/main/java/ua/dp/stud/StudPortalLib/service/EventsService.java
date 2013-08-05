/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
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

    Collection<Events> getEventsOnPage(Integer pageNumb, Integer eventsByPage, Boolean approve);

    Collection<Events> getAllEvents(Boolean approve);

    Events getEventsById(Integer id);

    void addImage(ImageImpl image);

    void deleteEvents(Events events);

    void updateEvents(Events events);

    void addEvents(Events eventsToAdd);
    
    Boolean isUnique(Events events);

    Collection<Events> getOnMainPage();
    
    Collection<Events> getEventsOfTypeByPage(Integer pageNumb, Integer eventsByPage,String type, Boolean approve);
    
    Integer getPagesCountOfType(int eByPage, EventsType type) ;
}
