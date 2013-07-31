package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ua.dp.stud.StudPortalLib.dao.DaoForApprove;
import ua.dp.stud.StudPortalLib.model.BaseImagesSupport;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.StudPortalLib.model.News;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: VladB
 * Date: 31.07.13
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 */
public abstract class DaoForApproveImpl<E extends BaseImagesSupport> extends BaseDaoImpl<E> implements DaoForApprove<E> {
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
    public void delete(Integer id) {
        E object = (E) getSession().get(persistentClass, id);
        ImageImpl image = object.getMainImage();
        if (image != null) {
            getSession().delete(image);
        }
        getSession().delete(object);
    }

    @Override
    public E getById(Integer id) {
        E object = (E) getSession().get(persistentClass, id);
        if (object != null) {
            Hibernate.initialize(object.getAdditionalImages());
        }
        return object;
    }

    @Override
    public Integer calcPages(Integer count, Integer perPage) {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }

    @Override
    public Integer getCount(Boolean approved) {
        return ((Long) getSession().createCriteria(persistentClass).setProjection(Projections.rowCount())
                .add(Restrictions.isNull("comment"))
                .add(Restrictions.eq("approved", approved))
                .uniqueResult()).intValue();
    }

    @Override
    public Integer getCountByAuthor(String author) {
        return ((Long) getSession().createCriteria(persistentClass).setProjection(Projections.rowCount())
                .add(Restrictions.eq("author", author))
                .uniqueResult()).intValue();
    }

    @Override
    public Collection<E> getPagesObjectByAuthor(String author, Integer pageNumb, Integer objByPage) {
        int firstResult = (pageNumb - 1) * objByPage;
        return getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("author", author)).add(Restrictions.isNull("comment"))
                .addOrder(Order.desc("id")).setFirstResult(firstResult).setMaxResults(objByPage).list();
    }

    @Override
    public Collection<E> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage) {
        int firstResult = (pageNumb - 1) * objByPage;
        return getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approved))
                .add(Restrictions.isNull("comment")).setFirstResult(firstResult)
                .setMaxResults(objByPage).addOrder(Order.desc("id")).list();
    }
}
