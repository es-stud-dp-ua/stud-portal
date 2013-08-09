/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao;

import java.util.Collection;
import ua.dp.stud.StudPortalLib.model.Events;
import ua.dp.stud.StudPortalLib.model.Tags;

/**
 *
 * @author Ольга
 */
public interface TagsDao extends BaseDao<Tags>{
       
   Tags getTagById(Integer id); 
}
