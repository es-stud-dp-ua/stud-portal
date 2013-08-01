/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao.impl;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.impl.DaoForApproveImpl;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.studie.model.Studie;

/**
 * @author Ольга
 */
@Repository("studieDao")
public class StudieDaoImpl extends DaoForApproveImpl<Studie> implements StudieDao {

    @Override
    public Studie getById(Integer id) {
        Studie studie = (Studie) getSession().get(Studie.class, id);
        Hibernate.initialize(studie.getAdditionalImages());
        Hibernate.initialize(studie.getFaculties());
        return studie;
    }
}
