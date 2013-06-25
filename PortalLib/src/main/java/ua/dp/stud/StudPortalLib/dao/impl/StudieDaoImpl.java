/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;


import java.util.Collection;
import org.hibernate.criterion.Order;
import ua.dp.stud.StudPortalLib.model.Studie;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ua.dp.stud.StudPortalLib.dao.StudieDao;
/**
 *
 * @author Ольга
 */
public class StudieDaoImpl extends HibernateDaoSupport implements StudieDao {
   
    @Override
    public Studie addStudie(Studie studie) {
        getSession().save(studie);
       return studie;
    }

    @Override
    public Studie getStudieById(Integer id) {
        //todo: use load
       return (Studie) getSession().get(Studie.class, id);
    }

    @Override
    public Studie updateStudie(Studie studie) {
        getSession().update(studie);
        return studie;
    }

    @Override
    public Collection<Studie> getAllStudies() {
         return (Collection<Studie>)getSession().createQuery("From Studie studie  ORDER BY studie.id desc").list();
    }

    @Override
    public void deleteStudie(Integer id) {
        Studie stud = (Studie) getSession().createQuery("Select studie from Studie studie Where studie.id = :id").setParameter("id", id).uniqueResult();
        ImageImpl image = stud.getMainImage();
        getSession().delete(image);
        getSession().delete(stud);
    }
    
    @Override
 public void addImage(ImageImpl image)
 {
      getSession().save(image);
 }
      @Override
    public ImageImpl getImageById(Long id)
	{//todo: use load
        return (ImageImpl) getSession().get(ImageImpl.class, id);
    }
    
    
    @Override
	public void deleteImage(Long id)
	{
		ImageImpl image = getImageById(id);
        image.getBase().getAdditionalImages().remove(image);
		image.setBase(null);
		getSession().delete(image);
	}  
}
