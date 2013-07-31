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

    public E save(E object);

    public E update(E object);
    
    public void delete(Integer id);

    public void deleteImage(Long id);

    public ImageImpl getImageById(Long id);

    public void addImage(ImageImpl image);

    public Integer getCount();
    
    public Collection<E> getAll();
    
    public E getById(Integer id);
}
