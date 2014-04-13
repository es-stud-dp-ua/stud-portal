package ua.dp.stud.studie.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.StudPortalLib.model.News;
import ua.dp.stud.studie.dao.OnlineCourseDao;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;

@Repository("onlineCourseDao")
public class OnlineCourseDaoImpl implements OnlineCourseDao{

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public OnlineCourse getOnlineCourseById(Integer id) {
        return (OnlineCourse) getSession().get(OnlineCourse.class, id);
    }

    @Override
    public void addOnlineCourse(OnlineCourse onlineCourse){
        getSession().save(onlineCourse);
    }

    @Override
    public void deleteOnlineCourse(Integer id){
        OnlineCourse onlineCourse = (OnlineCourse) getSession().get(OnlineCourse.class, id);
        getSession().delete(onlineCourse);
    }

    @Override
    public void updateOnlineCourse(OnlineCourse onlineCourse){
        getSession().update(onlineCourse);
    }

    @Override
    public List<OnlineCourse> getAll(){
        return getSession().createCriteria(OnlineCourse.class).addOrder(Order.desc("id")).list();
    }

    @Override
    public OnlineCourseType getOnlineCourseTypeById(Long id){
    	OnlineCourseType onlineCourseType = (OnlineCourseType) getSession().get(OnlineCourseType.class, id);
    	 Hibernate.initialize(onlineCourseType.getOnlineCourse());
    	return onlineCourseType;
    }

    @Override
    public void addOnlineCourseType(OnlineCourseType onlineCourseType) {
        getSession().save(onlineCourseType);
    }

    @Override
    public void deleteOnlineCourseType(Long id){
        OnlineCourseType onlineCourseType = (OnlineCourseType) getSession().get(OnlineCourseType.class, id);
        getSession().delete(onlineCourseType);
    }

    @Override
    public void updateOnlineCourseType(OnlineCourseType onlineCourseType){
        getSession().update(onlineCourseType);
    }

    @Override
    public List<OnlineCourseType> getAllOnlineCourseType(){
    	 List<OnlineCourseType> list = getSession().createCriteria(OnlineCourseType.class).list();
    	 for(OnlineCourseType type:list){
    		 Hibernate.initialize(type.getOnlineCourse());
    	 }
        return list;
    }

	@Override
	public List<OnlineCourse> getOnlineCourseByType(Long onlineCourseTypeId, Integer pageNumb, Integer courseByPage){
		int firstResult = (pageNumb - 1) * courseByPage;
		Query query = getSession().createQuery("from OnlineCourse where onlineCourseType.id = :onlineCourseTypeId ORDER BY id DESC");
		query.setParameter("onlineCourseTypeId", onlineCourseTypeId);
		List<OnlineCourse> list = (List<OnlineCourse>)query.setFirstResult(firstResult).setMaxResults(courseByPage).list();
        return list;
	}

    @Override
    public void initializeCountOfCourses(OnlineCourseType onlineCourseType){
        Query q = getSession().createQuery("SELECT count(id) FROM OnlineCourse WHERE onlineCourseType="+onlineCourseType.getId().toString());
        onlineCourseType.setCountOfCourses((Long) q.uniqueResult());
    }
    
    @Override
    public List<OnlineCourse> getOnlineCourseByTitle(String title, Integer pageNumb, Integer courseByPage){
    	int firstResult = (pageNumb - 1) * courseByPage;
    	Query query = getSession().createQuery("from OnlineCourse where onlineCourseName like :onlineCourseName ORDER BY id DESC");
		query.setParameter("onlineCourseName","%"+title+"%");
		List<OnlineCourse> list = (List<OnlineCourse>)query.setFirstResult(firstResult).setMaxResults(courseByPage).list();
        return list;
    }

    @Override
    public Collection<OnlineCourse> getOnlineCoursesOnPage(Integer pageNumb, Integer courseByPage) {
        int firstResult = (pageNumb - 1) * courseByPage;
        return (Collection<OnlineCourse>) getSession().createCriteria(OnlineCourse.class).addOrder(Order.desc("id"))
                .setFirstResult(firstResult).setMaxResults(courseByPage).list();

    }

    @Override
    public Integer getPagesCount(Integer coursesByPage) {
    	Query q = getSession().createQuery("SELECT count(id) FROM OnlineCourse");
        return ((Long) q.uniqueResult()).intValue()/coursesByPage+1; 
    }

    @Override
    public Boolean isDuplicateTopic(String name, Long id){
        if(id==null){
            List<OnlineCourse> list=getSession().createQuery("from OnlineCourse where onlineCourseName=:name").setParameter("name", name).list();
            return  (list.size()!=0);
        }else{
            List<OnlineCourse> list=getSession().createQuery("from OnlineCourse where onlineCourseName=:name and id=:id").setParameter("name", name).setParameter("id", id).list();
            return  (list.size()>1);
        }
    }


}