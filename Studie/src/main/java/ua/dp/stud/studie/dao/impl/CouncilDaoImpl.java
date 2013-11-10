package ua.dp.stud.studie.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.studie.dao.CouncilDao;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.CouncilMembers;

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
    	 Council council = (Council) getSession().get(Council.class, id);
         return council;
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

	public CouncilMembers getCouncilMembersById(Integer id){
		return (CouncilMembers) getSession().get(CouncilMembers.class, id);
	}

    public void addCouncilMembers(CouncilMembers councilMembers){
    	getSession().save(councilMembers);
    }

    public void deleteCouncilMembers(Integer id){
    	CouncilMembers councilMembers = (CouncilMembers) getSession().get(CouncilMembers.class, id);
        getSession().delete(councilMembers);
    }

    public void updateCouncilMembers(CouncilMembers councilMembers){
    	getSession().update(councilMembers);
    }

    public List<CouncilMembers> getAllCouncilMembers(){
    	return getSession().createCriteria(CouncilMembers.class).list();
    }

}