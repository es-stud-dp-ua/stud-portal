/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.service;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.Tags;

/**
 *
 * @author Ольга
 */
public interface TagsService {
   
    Tags getTagById(Integer id);
    
    void setTag(Tags tag);
    
    Collection<Tags> getAllTags();
    
    void deleteTags(Tags tags);
    
    void updateTags(Tags tag);
}
