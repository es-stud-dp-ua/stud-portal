/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.StudPortalLib.util.EventsType;

/**
 * @author Ольга
 */
@Service("eventsService")
@Transactional
public class EventsServiceImpl implements EventsService {
    @Autowired
    private EventsDao dao;

    @Transactional(readOnly = false)
    public void setDao(EventsDao dao) {
        this.dao = dao;
    }
    
    @Override
    public void incrementViews(Events event) {
        dao.incrementViews(event);
    }

    @Override
    @Transactional(readOnly = false)
    public void addEvents(Events eventsToAdd) {
        dao.save(eventsToAdd);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateEvents(Events events) {
        dao.save(events);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteEvents(Events events) {
        dao.delete(events.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void addImage(ImageImpl image) {
        dao.addImage(image);
    }

    @Override
    @Transactional(readOnly = true)
    public Events getEventsById(Integer id) {
        return dao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Events> getAllEvents(Boolean approve) {
        return dao.getAllEvents(approve);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Events> getEventsOnPage(Integer pageNumb, Integer eventsByPage, Boolean approve) {
        return dao.getObjectOnPage(approve, pageNumb, eventsByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Integer newsByPage) {
        return dao.calcPages(dao.getCount(), newsByPage);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteImage(ImageImpl image) {
        dao.deleteImage(image.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public ImageImpl getImageById(Long id) {
        return dao.getImageById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Events> getEventsByType(String type) {
        try {
            EventsType orgType = EventsType.valueOf(type);
            return dao.getEventsByType(orgType);
        } catch (Exception unused) {
            return null;
        }
    }

    /**
     * @param events
     * @return true if events with this name is present
     */
    @Override
    public Boolean isUnique(Events events) {
        return dao.getEventsByName(events.getTitle()) != null;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Events> getOnMainPage() {
        return dao.getOnMainPage();
    }
    
 @Transactional(readOnly = true)
    @Override
    public Collection<Events> getEventsOfTypeByPage(Integer pageNumb, Integer eventsByPage, String type, Boolean approve) {
            return dao.getEventsOfTypeOnPage(pageNumb, eventsByPage, type, approve);
    }
 
   @Override
    @Transactional(readOnly = true)
    public Integer getPagesCountOfType(int eByPage, EventsType type) {
        return dao.calcPages(dao.getCountOfType(type), eByPage);
    }
}
