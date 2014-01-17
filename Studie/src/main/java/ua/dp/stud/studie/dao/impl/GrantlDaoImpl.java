package ua.dp.stud.studie.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.studie.dao.GrantDao;
import ua.dp.stud.studie.model.Grant;

import java.util.Collection;

/**
 *           @author Lysenko Nikolai
 */

@Repository("grantDao")
public class GrantlDaoImpl implements GrantDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }


	@Override
	public Grant getGrantById(Integer id) {
		return (Grant) getSession().get(Grant.class, id);
	}

	@Override
	public Collection<Grant> getAllGrants() {
		return getSession().createCriteria(Grant.class).list();
	}

	@Override
	public void addGrant(Grant grant) {
		getSession().save(grant);
		
	}

	@Override
	public void deleteGrant(Integer id) {
		
		Grant grant = (Grant) getSession().get(Grant.class, id);
        getSession().delete(grant);
		
	}

	@Override
	public void updateGrant(Grant grant) {
		getSession().merge(grant);
		
	}

}