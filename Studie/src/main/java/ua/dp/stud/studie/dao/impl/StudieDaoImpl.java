/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao.impl;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.StudPortalLib.dao.impl.BaseDao;
import ua.dp.stud.StudPortalLib.model.ImageImpl;
import ua.dp.stud.studie.model.Studie;

import java.util.Collection;
import java.util.List;
import ua.dp.stud.studie.model.Faculties;

/**
 * @author Ольга
 */
@Repository("studieDao")
public class StudieDaoImpl extends BaseDao implements StudieDao {

    @Override
    public Studie addStudie(Studie studie) {
        getSession().save(studie);
        return studie;
    }

    @Override
    public Studie getStudieById(Integer id) {
        Studie studie = (Studie) getSession().get(Studie.class, id);
        Hibernate.initialize(studie.getAdditionalImages());
        Hibernate.initialize(studie.getFaculties());
        return studie;
    }

    @Override
    public Studie updateStudie(Studie studie) {
        getSession().update(studie);
        return studie;
    }

    @Override
    public Collection<Studie> getAllStudies() {
        return (Collection<Studie>) getSession().createQuery("From Studie studie  ORDER BY studie.id desc").list();
    }

    @Override
    public void deleteStudie(Integer id) {
        Studie stud = (Studie) getSession().createQuery("Select studie from Studie studie Where studie.id = :id").setParameter("id", id).uniqueResult();
        ImageImpl image = stud.getMainImage();
        getSession().delete(image);
        getSession().delete(stud);
    }

    @Override
    public void addImage(ImageImpl image) {
        getSession().save(image);
    }

    @Override
    public ImageImpl getImageById(Long id) {
        return (ImageImpl) getSession().get(ImageImpl.class, id);
    }

    @Override
    public void deleteImage(Long id) {
        ImageImpl image = getImageById(id);
        image.getBase().getAdditionalImages().remove(image);
        image.setBase(null);
        getSession().delete(image);
    }

    @Override
    public Faculties addFaculties(Faculties faculties) {
       getSession().save(faculties);
        return faculties; 
    }

    @Override
    public List<Faculties> getFacultiesOfStudie(Studie studie) {
       return   getSession().createQuery("Select faculties from Faculties faculties Where faculties.base = :id").setParameter("id", studie.getId()).list();
        
    }

    @Override
    public List<Faculties> getAllFaculties() {
           return (List<Faculties>) getSession().createQuery("From Faculties nameOfFaculties  ORDER BY nameOfFaculties.id desc").list();
      
    }
}
