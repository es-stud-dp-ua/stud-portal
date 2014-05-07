package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.StudPortalLib.dao.CourseDao;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.CoursesType;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;

import java.util.Collection;
import java.util.List;

/**
 *           @author Nazarenko K.V.
 */

@Repository("courseDao")
public class CourseDaoImpl extends DaoForApproveImpl<Course> implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Course getCourseById(Integer id) {
        Course course = (Course) getSession().get(Course.class, id);
        Hibernate.initialize(course.getKindOfCourse());
        return course;
    }

    @Override
    public void addCourse(Course course) {
        getSession().save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        Course course = (Course) getSession().get(Course.class, id);
        getSession().delete(course);
    }

    @Override
    public void updateCourse(Course course) {
        getSession().update(course);
    }

    @Override
    public List<Course> getAll() {
        return getSession().createCriteria(Course.class).addOrder(Order.asc("courseName")).list();
    }

    @Override
    public List<Course> getCoursesByKindAndType(String kindOfCourse, String coursesType){
        StringBuilder queryString = new StringBuilder("from Course where approved=:t and ");
        if (!"all".equals(kindOfCourse)){
            queryString.append("kindOfCourse.id = :kindOfCourse");
        } else {
            queryString.append("1=1");
        }
        queryString.append(" and ");
        if (!"all".equals(coursesType)) {
            queryString.append("coursesType = :coursesType");
        } else {
            queryString.append("1=1");
        }
        queryString.append(" Order By courseName asc");
        Query query = getSession().createQuery(queryString.toString());
        if (!"all".equals(kindOfCourse)) {
            query.setParameter("kindOfCourse", Long.parseLong(kindOfCourse));
        }
        if (!"all".equals(coursesType)) {
            query.setParameter("coursesType", CoursesType.valueOf(coursesType));
        }
        query.setParameter("t",true);
        return  (List<Course>)query.list() ;

    }

    @Override
    public KindOfCourse getKindOfCourseById(Long id) {
        return (KindOfCourse) getSession().get(KindOfCourse.class, id);

    }

    @Override
    public void addKindOfCourse(KindOfCourse kindOfCourse) {
        getSession().save(kindOfCourse);
    }

    @Override
    public void deleteKindOfCourse(Long id) {
        KindOfCourse kindOfCourse = (KindOfCourse) getSession().get(KindOfCourse.class, id);
        getSession().delete(kindOfCourse);
    }

    @Override
    public void updateKindOfCourse(KindOfCourse kindOfCourse) {
        getSession().update(kindOfCourse);
    }

    @Override
    public void initializeCountOfCourses(KindOfCourse kindOfCourse) {
        Query q = getSession().createQuery("SELECT count(id) FROM Course WHERE kindOfCourse="+kindOfCourse.getId().toString());
        kindOfCourse.setCountOfCourses((Long) q.uniqueResult());
    }

    @Override
    public Collection<Course> getAllCoursesByAuthor(String author){
        return getSession().getNamedQuery("Course.getByAuthor")
                .setParameter("author", author).list();
    }

    @Override
    public List<KindOfCourse> getAllKindOfCourse() {
        return getSession().createCriteria(KindOfCourse.class).list();
    }

    @Override
    public Boolean isDuplicateTopic(String name, Integer id){
        List<Course> list=getSession().createQuery("from Course where courseName=:name").setParameter("name", name).list();
        if(id==null){
            return  (list.size()!=0);
        }else{
            if(list.size()==1){
                return !(list.get(0).getId().equals(id));
            }else{
                return  (list.size()>1);
            }

        }
    }
}