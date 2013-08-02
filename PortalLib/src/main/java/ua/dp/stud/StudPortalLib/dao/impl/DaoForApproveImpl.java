package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Criteria;
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
 * @author Pikus Vladislav
 */
public abstract class DaoForApproveImpl<E extends BaseImagesSupport> extends BaseDaoImpl<E> implements DaoForApprove<E> {
    /**
     * Delete image
     *
     * @param id image id
     */
    @Override
    public void deleteImage(Long id) {
        //todo: find better approach
        ImageImpl image = getImageById(id);
        image.getBase().getAdditionalImages().remove(image);
        image.setBase(null);
        getSession().delete(image);
    }

    /**
     * Return image by id
     *
     * @param id image id
     * @return founded object
     */
    @Override
    public ImageImpl getImageById(Long id) {
        return (ImageImpl) getSession().get(ImageImpl.class, id);
    }

    /**
     * Save new image in DB
     *
     * @param image new image
     */
    @Override
    public void addImage(ImageImpl image) {
        getSession().save(image);
    }

    /**
     * Deleting current object by id
     *
     * @param id object id
     */
    @Override
    public void delete(Integer id) {
        E object = (E) getSession().get(persistentClass, id);
        ImageImpl image = object.getMainImage();
        if (image != null) {
            getSession().delete(image);
        }
        getSession().delete(object);
    }

    /**
     * Return object by id with initialization image list
     *
     * @param id object id
     * @return founded object
     */
    @Override
    public E getById(Integer id) {
        E object = (E) getSession().get(persistentClass, id);
        if (object != null) {
            Hibernate.initialize(object.getAdditionalImages());
        }
        return object;
    }

    /**
     * Calculate page
     *
     * @param count   page number
     * @param perPage object per page
     * @return calculated number
     */
    @Override
    public Integer calcPages(Integer count, Integer perPage) {
        return (count > 0) ? ((count - 1) / perPage + 1) : 0;
    }

    /**
     * Return object count by author
     *
     * @param author author of the object
     * @return object count
     */
    @Override
    public Integer getCountByAuthor(String author) {
        return ((Long) getSession().createCriteria(persistentClass).setProjection(Projections.rowCount())
                .add(Restrictions.eq("author", author))
                .uniqueResult()).intValue();
    }

    /**
     * Return list of object ob current page by author
     *
     * @param author    author of the object
     * @param pageNumb  page number
     * @param objByPage object per page
     * @return list of object
     */
    @Override
    public Collection<E> getPagesObjectByAuthor(String author, Integer pageNumb, Integer objByPage) {
        int firstResult = (pageNumb - 1) * objByPage;
        return getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("author", author))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).addOrder(Order.desc("id")).setFirstResult(firstResult).setMaxResults(objByPage).list();
    }

    /**
     * Return object count by approve
     *
     * @param approved approve
     * @return object count
     */
    @Override
    public Integer getCount(Boolean approved) {
        return ((Long) getSession().createCriteria(persistentClass).setProjection(Projections.rowCount())
                .add(Restrictions.isNull("comment"))
                .add(Restrictions.eq("approved", approved))
                .uniqueResult()).intValue();
    }

    /**
     * Return list of object ob current page by approve
     *
     * @param approved  approve
     * @param pageNumb  page number
     * @param objByPage object per pag
     * @return list of object
     */
    @Override
    public Collection<E> getObjectOnPage(Boolean approved, Integer pageNumb, Integer objByPage) {
        int firstResult = (pageNumb - 1) * objByPage;
        return getSession().createCriteria(persistentClass)
                .add(Restrictions.eq("approved", approved))
                .add(Restrictions.isNull("comment")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFirstResult(firstResult).setMaxResults(objByPage).addOrder(Order.desc("id")).list();
    }
}
