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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.Tags;
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
        //получаем все события до Этого момента времени, упорядоченые по невозрастанию
        Collection<Events> nearesEvents = getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approve))
                .add(Restrictions.eq("eventsType", type))
                .add(Restrictions.ge("eventDateStart", new Date()))
                .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(firstResult).setMaxResults(eventsPerPage).addOrder(Order.asc("eventDateStart")).list();
        //получаем события, которые прошли уже
        Collection<Events> endedEvents = getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approve))
                .add(Restrictions.eq("eventsType", type))
                .add(Restrictions.le("eventDateStart", new Date()))
                .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(firstResult).setMaxResults(eventsPerPage - nearesEvents.size()).addOrder(Order.desc("eventDateStart")).list();
        nearesEvents.addAll(endedEvents);
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
    public Events getEventsById(Integer id) {
        Events events = (Events) getSession().get(Events.class, id);
        Hibernate.initialize(events.getTags());
        return events;
    }

    @Override
    public Collection<Events> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage) {
        int firstResult = (pageNumb - 1) * objByPage;
        //получаем все события до Этого момента времени, упорядоченые по невозрастанию
        Collection<Events> nearesEvents = getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approved))
                .add(Restrictions.ge("eventDateStart", new Date()))
                .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(firstResult).setMaxResults(objByPage).addOrder(Order.asc("eventDateStart")).list();
        //получаем события, которые прошли уже
        Collection<Events> endedEvents = getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approved))
                .add(Restrictions.le("eventDateStart", new Date()))
                .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(firstResult).setMaxResults(objByPage - nearesEvents.size()).addOrder(Order.desc("eventDateStart")).list();
        nearesEvents.addAll(endedEvents);
        return nearesEvents;
    }
}
