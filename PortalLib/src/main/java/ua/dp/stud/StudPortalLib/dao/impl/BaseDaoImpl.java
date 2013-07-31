package ua.dp.stud.StudPortalLib.dao.impl;

import java.lang.reflect.ParameterizedType;
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
  
    private Class<E> persistentClass;
 
   
    public BaseDaoImpl() {
        /**
         * define a class
         */
        this.persistentClass = (Class<E>) ((ParameterizedType) getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
     }
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
   public Integer getCount(){
        return ((Long) getSession().createCriteria(persistentClass).setProjection(Projections.rowCount())
                .uniqueResult()).intValue(); 
   }
        
   @Override      
  public Collection<E> getAll(){
        return getSession().createCriteria(persistentClass).addOrder(Order.desc("id")).list();
   }
   
   @Override
       public E getById(Integer id){
       return (E) getSession().get(persistentClass, id);
   }
   
     @Override
    public void deleteObject(Integer id) {
        E object = (E) getSession().get(persistentClass, id);
        getSession().delete(object);
    }
}
