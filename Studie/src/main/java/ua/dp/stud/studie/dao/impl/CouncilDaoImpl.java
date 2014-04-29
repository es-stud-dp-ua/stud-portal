package ua.dp.stud.studie.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    	 Hibernate.initialize(council.getCouncilMembers());
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
        return getSession().createCriteria(Council.class).addOrder(Order.asc("studie")).list();
    }

    @Override
    public CouncilMembers getCouncilMembersById(Integer id){
		return (CouncilMembers) getSession().get(CouncilMembers.class, id);
	}

	@Override
	public void addCouncilMembers(CouncilMembers councilMembers){
    	getSession().save(councilMembers);
    }

    @Override
    public void deleteCouncilMembers(Integer id){
    	CouncilMembers councilMembers = (CouncilMembers) getSession().get(CouncilMembers.class, id);
        getSession().delete(councilMembers);
    }

    @Override
    public void updateCouncilMembers(CouncilMembers councilMembers){
    	getSession().merge(councilMembers);
    }

    @Override
    public List<CouncilMembers> getAllCouncilMembers(){
    	return getSession().createCriteria(CouncilMembers.class).list();
    }

    @Override
    public Boolean isDuplicateTopic(Integer id,Integer idCouncil){
        List<Council> list=getSession().createQuery("From Council where studie.id=:Sid").setParameter("Sid",id).list();
        if(idCouncil==null){
              return  (list.size()!=0);
        }else{
            if(list.size()==1)
                return !(list.get(0).getId().equals(idCouncil));
            else{
                return  (list.size()>1);
            }
        }
    }

}