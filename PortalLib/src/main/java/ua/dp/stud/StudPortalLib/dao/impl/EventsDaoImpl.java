/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.Tags;
import ua.dp.stud.StudPortalLib.util.Direction;
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
    public Collection<Events> getEventsOfTypeOnPage(Integer pageNumb, Integer eventsPerPage, String type, Boolean approve,/* -Boolean future*/ Direction direct,Date date)
    {
        int firstResult = (pageNumb - 1) * eventsPerPage;
        Collection<Events> nearesEvents = null;
     /*  if (future) {
            nearesEvents = getSession().createCriteria(Events.class)
                    .add(Restrictions.eq("approved", approve))
                    .add(Restrictions.eq("type", EventsType.valueOf(type)))
                    .add(Restrictions.ge("eventDateStart", new Date()))
                    .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFirstResult(firstResult).setMaxResults(eventsPerPage).addOrder(Order.asc("eventDateStart")).list();
        } else {
            nearesEvents = getSession().createCriteria(Events.class)
                    .add(Restrictions.eq("approved", approve))
                    .add(Restrictions.eq("type", EventsType.valueOf(type)))
                    .add(Restrictions.le("eventDateStart", new Date()))
                    .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFirstResult(firstResult).setMaxResults(eventsPerPage).addOrder(Order.asc("eventDateStart")).list();
        }

      */
        if (direct==Direction.FUTURE)
        {
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart>=:DateNow AND type=:etype AND approved=:approve");
            query.setDate("DateNow",new Date());
            query.setParameter("approve",approve);
            query.setParameter("etype",EventsType.valueOf(type));
            nearesEvents=query.list();
        }
        else
        if (direct==Direction.PREVIOS)
        {
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart<=:DateNow AND type=:etype AND approved=:approve");
            query.setDate("DateNow",new Date());
            query.setParameter("approve",approve);
            query.setParameter("etype",EventsType.valueOf(type));
            nearesEvents=query.list();
        }
        else
        if (direct==Direction.DAY)
        {   Date tmpb=new Date();
            tmpb.setHours(0);
            tmpb.setSeconds(0);
            tmpb.setMinutes(0);
            Date tmpe=new Date();
            tmpe.setHours(23);
            tmpe.setSeconds(59);
            tmpe.setMinutes(59);
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart>=:DateNowB AND eventDateStart<=:DateNowE AND type=:etype AND approved=:approve");
            query.setDate("DateNowB",tmpb);
            query.setDate("DateNowE",tmpe);
            query.setParameter("approve",approve);
            query.setParameter("etype",EventsType.valueOf(type));
            nearesEvents=query.list();
        }
        else
        {
            Query query=getSession().createQuery("FROM Events WHERE type=:etype AND approved=:approve");
            query.setParameter("etype",EventsType.valueOf(type));
            query.setParameter("approve",approve);
            nearesEvents=query.list();

        }

       /* Collection<Events> nearesOnPageEvents = null;
        for (int i=0;i<nearesEvents.size();i++)
            if (i<firstResult) nearesOnPageEvents.add(nearesEvents.iterator().next()); */
        return nearesEvents;
    }

    @Override
    public Collection<Events> getAllEvents(Boolean approved) {
        return getSession().getNamedQuery("Events.getByApproved")
                .setParameter("approved", approved).list();
    }

    @Override
    public Events save(Events event, List<Tags> tagsToAdd) {
        tagsToAdd = (ArrayList<Tags>) tagsToAdd;
        for (int i = 0; i < tagsToAdd.size(); i++) {//для каждого тэга из тэгов переданного ивента
            Tags tag = (Tags) getSession().createQuery("From Tags tag WHERE tag.name= :nameParam").setParameter("nameParam", tagsToAdd.get(i).getName()).uniqueResult();
            if (tag != null) {//для  одинакового тэга:
                tagsToAdd.get(i).setEvents(tag.getEvents()); //забираем его ивенты в тэг из переданного ивента
                for (Events curEvent : tag.getEvents()) {//для каждого забраного ивента
                    curEvent.getTags().remove(tag);                    //У каждого ивета удаляем старый тэг
                    curEvent.addTag(tagsToAdd.get(i));                 //добавляем тэг из переданного ивента(новый тэг)
                }
                tag.setEvents(null);
                getSession().delete(tag);            //удаляем один повторяющийся тэг
            }
        }
        getSession().save(event);                           //сохраняем ивент
        return event;
    }

    @Override
    public Events getEventsByName(String title) {
        List<Events> events = (List<Events>) getSession().createQuery("From Events event WHERE event.title= :title2").setParameter("title2", title).setMaxResults(1).list();
        if (!events.isEmpty()) {
            Hibernate.initialize(events.get(0).getTags());
            return events.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Events> getOnMainPage() {
        return getSession().createCriteria(Events.class).addOrder(Order.desc("publication"))
                .add(Restrictions.isNull("comment")).add(Restrictions.eq("approved", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setMaxResults(5).list();
    }

    @Override
    public Integer getCountOfType(EventsType type2, Boolean approved, Boolean futureEvents) {
        if (futureEvents) {
            return ((Long) getSession().createQuery("Select Count(*) From Events WHERE type= :type2 and approved=true and eventDateStart>=:dateNow")
                    .setDate("dateNow", new Date())
                    .setParameter("type2", type2).uniqueResult()).intValue();
        } else {
            return ((Long) getSession().createQuery("Select Count(*) From Events WHERE type= :type2 and approved=true and eventDateStart<=:dateNow")
                    .setDate("dateNow", new Date())
                    .setParameter("type2", type2).uniqueResult()).intValue();
        }

    }

    @Override
    public Integer getCount(Boolean approved, Boolean futureEvents) {
        if (futureEvents == null) {
            return ((Long) getSession().createQuery("Select Count(*) From Events WHERE  approved=:approve")
                    .setParameter("approve", approved).uniqueResult()).intValue();
        } else if (futureEvents) {
            return ((Long) getSession().createQuery("Select Count(*) From Events WHERE  approved=:approve and eventDateStart>=:dateNow")
                    .setDate("dateNow", new Date())
                    .setParameter("approve", approved).uniqueResult()).intValue();
        } else {
            return ((Long) getSession().createQuery("Select Count(*) From Events WHERE  approved=:approve and eventDateStart<=:dateNow")
                    .setDate("dateNow", new Date())
                    .setParameter("approve", approved).uniqueResult()).intValue();
        }
    }

    @Override
    public void incrementViews(Events event) {
        event.setViews(event.getViews() + 1);
        getSession().update(event);

    }

    @Override
    public Events getEventsById(Integer id) {
        Events events = (Events) getSession().get(Events.class, id);
        Hibernate.initialize(events.getTags());
        return events;
    }

    @Override
    public Collection<Events> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage,Direction direct,Date date) {
        int firstResult = (pageNumb - 1) * objByPage;
        Collection<Events> nearesEvents = null;
        //return all events (for event panel)
      /*  if(future==null){
            nearesEvents = getSession().createCriteria(persistentClass)
                    .add(Restrictions.eq("approved", approved))
                    .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFirstResult(firstResult).setMaxResults(objByPage).addOrder(Order.asc("eventDateStart")).list();
        }else
            //return future events
        if (future) {
            nearesEvents = getSession().createCriteria(persistentClass)
                    .add(Restrictions.eq("approved", approved))
                    .add(Restrictions.ge("eventDateStart", new Date()))
                    .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFirstResult(firstResult).setMaxResults(objByPage).addOrder(Order.asc("eventDateStart")).list();
            //return old events
        } else {
            nearesEvents = getSession().createCriteria(persistentClass)
                    .add(Restrictions.eq("approved", approved))
                    .add(Restrictions.le("eventDateStart", new Date()))
                    .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFirstResult(firstResult).setMaxResults(objByPage).addOrder(Order.asc("eventDateStart")).list();
        }           */
        if (direct==Direction.FUTURE)
        {
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart>=:DateNow AND approved=:approve ORDER BY eventDateStart asc");
            query.setDate("DateNow",date);
            query.setParameter("approve",approved);
            nearesEvents=query.list();
        }
        else
        if (direct==Direction.PREVIOS)
        {
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart<:DateNow AND approved=:approve ORDER BY eventDateStart asc");
            query.setDate("DateNow",date);
            query.setParameter("approve",approved);
            nearesEvents=query.list();
        }
        else
        if (direct==Direction.DAY)
        {   Date tmpb=date;
            tmpb.setHours(0);
            tmpb.setSeconds(0);
            tmpb.setMinutes(0);
            Date tmpe=date;
            tmpe.setHours(23);
            tmpe.setSeconds(59);
            tmpe.setMinutes(59);
            Query query=getSession().createQuery("FROM Events WHERE eventDateStart>=:DateNowB AND eventDateStart<=:DateNowE AND  approved=:approve ORDER BY eventDateStart asc ");
            query.setDate("DateNowB",tmpb);
            query.setDate("DateNowE",tmpe);
            query.setParameter("approve",approved);
            nearesEvents=query.list();
        }
        else
        {
            Query query=getSession().createQuery("FROM Events WHERE approved=:approve ORDER BY eventDateStart asc");
            query.setParameter("approve",approved);
            nearesEvents=query.list();

        }

        /*Collection<Events> nearestOnPageEvents = null;
        for (int i=0;i<nearesEvents.size();i++)
            if (i<firstResult) nearestOnPageEvents.add(nearesEvents.iterator().next());
        */
        return nearesEvents;
    }

 /*   public List<Events> getSortedEvents()
    {
       Query query = getSession().createQuery("from Events order by eventDateStart");
       List<Events> list = (List<Events>)query.list();
        return list;
    }  */
}
