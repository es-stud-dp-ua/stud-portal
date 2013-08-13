/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao;

import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

/**
 *
 * @author Ольга
 */
public interface BaseDao<E> {

    E save(E object);

    E update(E object);
    
    void delete(Integer id);

    Integer getCount();
    
    Collection<E> getAll();
    
    E getById(Integer id);
}
