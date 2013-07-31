/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.util.EventsType;


/**
 * @author Ольга
 */
@Repository("eventsDao")
public class EventsDaoImpl extends BaseDaoImpl<Events> implements EventsDao {

    @Override
    public Collection<Events> getEventsByType(EventsType type) {
        return getSession().createCriteria(Events.class)
                .add(Restrictions.eq("eventsType", type))
                .addOrder(Order.desc("id")).list();
    }

    @Override
    public Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer orgsPerPage, EventsType type, Boolean approve) {
        int firstResult = (pageNumb - 1) * orgsPerPage;
        return (Collection<Events>) getSession().createQuery("From Events events WHERE events.eventsType= :type and events.approved=:approve_ and events.comment is null ORDER BY events.id desc").setParameter("type", type).setParameter("approve_", approve).setFirstResult(firstResult).setMaxResults(orgsPerPage).list();
    }

    @Override
    public Collection<Events> getEventsOnPage(Integer pageNumb, Integer orgsPerPage, Boolean approve) {
        int firstResult = (pageNumb - 1) * orgsPerPage;
        return (Collection<Events>) getSession().createQuery("From Events events WHERE  events.approved=:approve_ and events.comment is null ORDER BY events.id desc").setParameter("approve_", approve).setFirstResult(firstResult).setMaxResults(orgsPerPage).list();
    }

    @Override
    public Collection<Events> getAllEvents(Boolean approved) {
        return getSession().getNamedQuery("Events.getByApproved")
                .setParameter("approved", approved).list();
    }

    @Override
    public Integer calcPages(Integer count, Integer perPage) {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }
}
