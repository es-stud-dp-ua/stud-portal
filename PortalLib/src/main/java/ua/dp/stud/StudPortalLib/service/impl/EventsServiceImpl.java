/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.EventsDao;
import ua.dp.stud.StudPortalLib.dao.TagsDao;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.Tags;
import ua.dp.stud.StudPortalLib.service.EventsService;
import ua.dp.stud.StudPortalLib.util.Direction;
import ua.dp.stud.StudPortalLib.util.EventsType;

/**
 * @author Ольга
 */
@Service("eventsService")
@Transactional
public class EventsServiceImpl implements EventsService {
    @Autowired
    private EventsDao dao;
    
    @Autowired
    private TagsDao tagsDao;
    
    @Transactional(readOnly = true)
    @Override
    public Integer getPagesCountByAuthor(String author, Integer eventsPerPage) {
        return dao.calcPages(dao.getCountByAuthor(author), eventsPerPage);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Events> getPagesEventsByAuthor(String author, Integer pageNumb, Integer eventsPerPage) {
        return dao.getPagesObjectByAuthor(author, pageNumb, eventsPerPage);
    }

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
    public void addEvents(Events eventsToAdd,List<Tags> tags) {
        dao.save(eventsToAdd,tags);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateEvents(Events events) {
        dao.update(events);
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
        return dao.getEventsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Events> getAllEvents(Boolean approve) {
        return dao.getAllEvents(approve);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Events> getEventsOnPage(Integer pageNumb, Integer eventsByPage, Boolean approve,Direction direct,Date date) {
        return dao.getObjectOnPage(approve, pageNumb, eventsByPage,direct,date);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Integer newsByPage) {
        return dao.calcPages(dao.getCount(), newsByPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getPagesCount(Boolean approved, Integer newsPerPage, Boolean futureEvents) {
        return dao.calcPages(dao.getCount(approved,futureEvents), newsPerPage);
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
     *
     * @return true if events with this name is present
     */
    @Override
    public Boolean isUnique(String name) {
        
        return dao.getEventsByName(name) == null;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Events> getOnMainPage() {
        return dao.getOnMainPage();
    }
    
 @Transactional(readOnly = true)
    @Override
    public Collection<Events> getEventsOfTypeByPage(Integer pageNumb, Integer eventsByPage, String type, Boolean approve,Direction direct,Date date) {
            return dao.getEventsOfTypeOnPage(pageNumb, eventsByPage, type, approve,direct,date);
    }
 
   @Override
    @Transactional(readOnly = true)
    public Integer getPagesCountOfType(int eByPage, EventsType type,Boolean approved, Boolean futureEvents) {
        return dao.calcPages(dao.getCountOfType(type,approved,futureEvents), eByPage);
    }
   
   /**TAGS */
   @Transactional(readOnly = false)
    public void setDao(TagsDao dao) {
        this.tagsDao = dao;
    }
     
    @Override
    public Tags getTagById(Integer id) {
        return tagsDao.getTagById(id);
    }

    @Override
    public void setTag(Tags tag) {
       tagsDao.save(tag);
    }

    @Override
    public Collection<Tags> getAllTags() {
        return tagsDao.getAll();
    }

    @Override
    public void deleteTags(Tags tags) {
        tagsDao.delete(tags.getId());
    }

    @Override
    public void updateTags(Tags tag) {
        tagsDao.update(tag);
    }



}
