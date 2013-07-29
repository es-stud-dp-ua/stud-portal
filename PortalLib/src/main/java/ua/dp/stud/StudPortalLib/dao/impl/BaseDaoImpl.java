package ua.dp.stud.StudPortalLib.dao.impl;

import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dp.stud.StudPortalLib.dao.BaseDao;
import ua.dp.stud.StudPortalLib.dao.NewsDao;
import ua.dp.stud.StudPortalLib.dao.OrganizationDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

/**
 * @author: Pikus Vladislav
 */
public abstract class BaseDaoImpl<E> implements BaseDao<E>{

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Integer calcPages(Integer count, Integer perPage) {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }

    @Override
    public E addObject(E object) {
        getSession().save(object);
        return (object);
    }

    @Override
    public E updateObject(E object) {
        getSession().update(object);
        return object;
    }

    @Override
    public void deleteImage(Long id) {
        //todo: find better approach
        ImageImpl image = getImageById(id);
        image.getBase().getAdditionalImages().remove(image);
        image.setBase(null);
        getSession().delete(image);
    }

    @Override
    public ImageImpl getImageById(Long id) {
        return (ImageImpl) getSession().get(ImageImpl.class, id);
    }

    @Override
    public void addImage(ImageImpl image) {
        getSession().save(image);
    }

    @Override
    public Collection<E> getAllObjects(Boolean approve, E object) {
        return getSession().createCriteria(object.getClass()).add(Restrictions.eq("approved", approve)).addOrder(Order.desc("id")).list();
    }

    @Override
    public Integer getCount(Boolean approve, E object) {
        return ((Long) getSession().createCriteria(object.getClass()).add(Restrictions.eq("approved", approve)).setProjection(Projections.rowCount())
                .uniqueResult()).intValue();
    }
    @Override
       public Collection<E> getObjectsOnPage(Boolean approved, Integer pageNumb, Integer newsByPage, E object) {
        int firstResult = (pageNumb - 1) * newsByPage;
        return getSession().createCriteria(object.getClass()).add(Restrictions.eq("approved", approved)).setFirstResult(firstResult).setMaxResults(newsByPage).list();
    }
       
    @Override
     public Integer getCountByAuthor(String author, E object) {
        return ((Long) getSession().createCriteria(object.getClass())
                .add(Restrictions.eq("author", author)).uniqueResult()).intValue();
    }
}
