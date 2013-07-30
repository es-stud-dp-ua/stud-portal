/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.util.EventsType;

/**
 *
 * @author Ольга
 */
public interface EventsDao<E> extends BaseDao<E> {

    void deleteEvent(Integer id);

    Collection<Events> getEventsByType(EventsType type);

    public Events getEventsById(Integer id);

    public Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer orgsPerPage, EventsType type, Boolean approve);

    public Collection<Events> getEventsOnPage(Integer pageNumb, Integer orgsPerPage, Boolean approve);

    public Collection<Events> getAllEvents(Boolean approved);
    
    Integer calcPages(Integer count, Integer perPage);
}
