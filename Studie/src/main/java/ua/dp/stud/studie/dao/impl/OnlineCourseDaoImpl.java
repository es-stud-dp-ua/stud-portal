package ua.dp.stud.studie.dao.impl;

import java.util.List;

import com.sun.org.apache.xpath.internal.operations.*;
import com.sun.org.apache.xpath.internal.operations.String;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.studie.dao.OnlineCourseDao;
import ua.dp.stud.studie.model.OnlineCourse;
import ua.dp.stud.studie.model.OnlineCourseType;

@Repository("onlineCourseDao")
public class OnlineCourseDaoImpl implements OnlineCourseDao
{

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession()
    {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public OnlineCourse getOnlineCourseById(Integer id)
    {
        OnlineCourse onlineCourse = (OnlineCourse) getSession().get(OnlineCourse.class, id);
        Hibernate.initialize(onlineCourse.getOnlineCourseType());
        return onlineCourse;
    }

    @Override
    public void addOnlineCourse(OnlineCourse onlineCourse)
    {
        getSession().save(onlineCourse);
    }

    @Override
    public void deleteOnlineCourse(Integer id)
    {
        OnlineCourse onlineCourse = (OnlineCourse) getSession().get(OnlineCourse.class, id);
        getSession().delete(onlineCourse);
    }

    @Override
    public void updateOnlineCourse(OnlineCourse onlineCourse)
    {
        getSession().update(onlineCourse);
    }

    @Override
    public List<OnlineCourse> getAll()
    {
        return getSession().createCriteria(OnlineCourse.class).list();
    }

    @Override
    public OnlineCourseType getOnlineCourseTypeById(Long id)
    {
        return (OnlineCourseType) getSession().get(OnlineCourseType.class, id);
    }

    @Override
    public void addOnlineCourseType(OnlineCourseType onlineCourseType)
    {
        getSession().save(onlineCourseType);
    }

    @Override
    public void deleteOnlineCourseType(Long id)
    {
        OnlineCourseType onlineCourseType = (OnlineCourseType) getSession().get(OnlineCourseType.class, id);
        getSession().delete(onlineCourseType);
    }

    @Override
    public void updateOnlineCourseType(OnlineCourseType onlineCourseType)
    {
        getSession().update(onlineCourseType);
    }

    @Override
    public List<OnlineCourseType> getAllOnlineCourseType()
    {
        return getSession().createCriteria(OnlineCourseType.class).list();
    }

	@Override
	public List<OnlineCourse> getOnlineCourseByType(Integer onlineCourseTypeId)
	{
		Query query = getSession().createQuery("from OnlineCourse where onlineCourseType.id = :onlineCourseTypeId");
		query.setParameter("onlineCourseTypeId", onlineCourseTypeId);
		List<OnlineCourse> list = (List<OnlineCourse>)query.list();
        return list;
	}

    @Override
    public void initializeCountOfCourses(OnlineCourseType onlineCourseType){
        Query q = getSession().createQuery("SELECT count(id) FROM Course WHERE kindOfCourse="+onlineCourseType.getId().toString());
        onlineCourseType.setCountOfCourses((Long) q.uniqueResult());
    };
}