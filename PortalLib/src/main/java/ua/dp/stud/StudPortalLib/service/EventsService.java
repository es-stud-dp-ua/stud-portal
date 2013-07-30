/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

/**
 *
 * @author Ольга
 */
public interface EventsService {

    public Collection<Events> getEventsByType(String type);

    public ImageImpl getImageById(Long id);

    public void deleteImage(ImageImpl image);

    public Integer getPagesCount(Integer newsByPage);

    public Collection<Events> getEventsOnPage(Integer pageNumb, Integer eventsByPage, Boolean approve);

    public Collection<Events> getAllEvents(Boolean approve);

    public Events getEventsById(Integer id);

    public void addImage(ImageImpl image);

    public void deleteEvents(Events events);

    public void updateEvents(Events events);

    public void addEvents(Events eventsToAdd);
}
