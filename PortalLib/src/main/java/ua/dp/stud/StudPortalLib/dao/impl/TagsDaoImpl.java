/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.TagsDao;
import ua.dp.stud.StudPortalLib.model.Tags;

/**
 *
 * @author Ольга
 */
@Repository("tagsDao")
public class TagsDaoImpl extends BaseDaoImpl<Tags> implements TagsDao{

    @Override
    public Tags getTagById(Integer id) {
        Tags tags = (Tags) getSession().get(Tags.class, id);
        Hibernate.initialize(tags.getEvents());
        return tags;  
    }
    
}
