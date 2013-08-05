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

    Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer orgsPerPage, EventsType type, Boolean approve);

    Collection<Events> getAllEvents(Boolean approved);
    
    Events getEventsByName(String title);

    Collection<Events> getOnMainPage();
    
    Integer getCountOfType(EventsType type);
}
