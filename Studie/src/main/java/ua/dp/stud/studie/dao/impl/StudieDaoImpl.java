/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao.impl;


import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import ua.dp.stud.StudPortalLib.dao.impl.DaoForApproveImpl;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.studie.model.Studie;

import java.util.Collection;
import java.util.List;

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

    @Override
    public Collection<Studie> getAll() {
        return getSession().createCriteria(Studie.class).addOrder(Order.asc("title")).list();
    }

    @Override
    public Boolean isDuplicateTopic(String name, Integer id){
        List<Studie> studieList=getSession().createQuery("from Studie where title=:name").setParameter("name",name).list();
        if(id==null){
            return  (studieList.size()!=0);
        }else{
            if(studieList.size()==1){
                return !(studieList.get(0).getId().equals(id));
            } else {
                return (studieList.size()>1);
            }
        }
    }
}

