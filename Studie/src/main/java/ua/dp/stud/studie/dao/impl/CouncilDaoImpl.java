package ua.dp.stud.studie.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.studie.dao.CouncilDao;
import ua.dp.stud.studie.model.Council;

import java.util.List;

/**
 *           @author Lysenko Nikolai
 */

@Repository("councilDao")
public class CouncilDaoImpl implements CouncilDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Council getCouncilById(Integer id) {
         return (Council) getSession().get(Council.class, id);
    }

    @Override
    public void addCouncil(Council council) {
        getSession().save(council);
    }

    @Override
    public void deleteCouncil(Integer id) {
        Council council = (Council) getSession().get(Council.class, id);
        getSession().delete(council);
    }

    @Override
    public void updateCouncil(Council council) {
        getSession().update(council);
    }

    @Override

    public List<Council> getAll() {
        return getSession().createCriteria(Council.class).list();
    }

}