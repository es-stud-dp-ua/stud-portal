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
public interface EventsDao extends DaoForApprove<Events> {

    Collection<Events> getEventsByType(EventsType type);

    public Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer orgsPerPage, EventsType type, Boolean approve);

    public Collection<Events> getAllEvents(Boolean approved);
    
    public Events getEventsByName(String title);
}
