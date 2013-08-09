/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.StudPortalLib.util.EventsType;


/**
 * @author Ольга
 */
@Repository("eventsDao")
public class EventsDaoImpl extends DaoForApproveImpl<Events> implements EventsDao {

    @Override
    public Collection<Events> getEventsByType(EventsType type) {
        return getSession().createCriteria(Events.class)
                .add(Restrictions.eq("eventsType", type))
                .addOrder(Order.desc("id")).list();
    }

    @Override
    public Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer eventsPerPage, String type, Boolean approve) {
        int firstResult = (pageNumb - 1) * eventsPerPage;
        EventsType type2=EventsType.valueOf(type);
        return (Collection<Events>) getSession().createQuery("From Events events WHERE events.type= :type2 and events.approved=:approve_ and events.comment is null ORDER BY events.id desc").setParameter("type2", type2).setParameter("approve_", approve).setFirstResult(firstResult).setMaxResults(eventsPerPage).list();
    }

    @Override
    public Collection<Events> getAllEvents(Boolean approved) {
        return getSession().getNamedQuery("Events.getByApproved")
                .setParameter("approved", approved).list();
    }

    @Override
    public Events getEventsByName(String title) {
        Events events = (Events) getSession().get(Events.class, title);
         Hibernate.initialize(events.getTags());
        return events;
    }

    
    @Override
    public Collection<Events> getOnMainPage() {
        return getSession().createCriteria(Events.class).addOrder(Order.desc("publication"))
                .add(Restrictions.isNull("comment")).add(Restrictions.eq("approved", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setMaxResults(5).list();
    }
    
     @Override
    public Integer getCountOfType(EventsType type2) {
        return ((Long) getSession().createQuery("Select Count(*) From Events WHERE type= :type2 and approved=true")
                .setParameter("type2", type2).uniqueResult()).intValue();
    }
     
     @Override
    public void incrementViews(Events event) {
        event.setViews(event.getViews() + 1);
        getSession().update(event);

    }
     
     @Override
     public  Events getEventsById(Integer id){
        Events events = (Events) getSession().get(Events.class, id);
        Hibernate.initialize(events.getTags());
        return events;  
     }
}
