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

    public void setSessionFactory(SessionFactory sessionFactory);

    public Session getSession();

    public Integer calcPages(Integer count, Integer perPage);

    public E addObject(E object);

    public E updateObject(E object);

    public void deleteImage(Long id);

    public ImageImpl getImageById(Long id);

    public void addImage(ImageImpl image);

    public Collection<E> getAllObjects(Boolean approve, E object);

    public Integer getCount(Boolean approve, E object);

    public Collection<E> getObjectsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage, E object);

    public Integer getCountByAuthor(String author, E object);
}
