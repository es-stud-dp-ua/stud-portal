/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service.impl;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.StudPortalLib.dao.TagsDao;
import ua.dp.stud.StudPortalLib.model.Tags;
import ua.dp.stud.StudPortalLib.service.TagsService;

/**
 *
 * @author Ольга
 */
@Service("tagsService")
@Transactional
public class TagsServiceImpl implements TagsService{

    @Autowired
    private TagsDao dao;
    
     @Transactional(readOnly = false)
    public void setDao(TagsDao dao) {
        this.dao = dao;
    }
     
    @Override
    public Tags getTagById(Integer id) {
        return dao.getTagById(id);
    }

    @Override
    public void setTag(Tags tag) {
       dao.save(tag);
    }

    @Override
    public Collection<Tags> getAllTags() {
        return dao.getAll();
    }

    @Override
    public void deleteTags(Tags tags) {
        dao.delete(tags.getId());
    }

    @Override
    public void updateTags(Tags tag) {
        dao.update(tag);
    }
    
}
