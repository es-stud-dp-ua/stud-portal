package ua.dp.stud.StudPortalLib.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.StudPortalLib.dao.CourseDao;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.model.CoursesType;
import ua.dp.stud.StudPortalLib.model.KindOfCourse;

import java.util.List;

/**
 *           @author Nazarenko K.V.
 */

@Repository("courseDao")
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
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
        return getSession().createCriteria(Course.class).list();
    }

    @Override
    public List<Course> getCoursesByKindAndType(String kindOfCourse, String coursesType)
    {
        StringBuilder queryString = new StringBuilder("from Course where ");
        if (!kindOfCourse.equals("all")){
            queryString.append("kindOfCourse.typeId = :kindOfCourse");
        } else {
            queryString.append("1=1");
        }
        queryString.append(" and ");
        if (!coursesType.equals("all")) {
            queryString.append("coursesType = :coursesType");
        } else {
            queryString.append("1=1");
        }
        Query query = getSession().createQuery(queryString.toString());
        if (!kindOfCourse.equals("all")) {
            query.setParameter("kindOfCourse", Integer.parseInt(kindOfCourse));
        }
        if (!coursesType.equals("all")) {
            query.setParameter("coursesType", CoursesType.valueOf(coursesType));
        }
        List<Course> list = (List<Course>)query.list();
        return list;
    }

    @Override
    public KindOfCourse getKindOfCourseById(Integer id) {
        return (KindOfCourse) getSession().get(KindOfCourse.class, id);

    }

    @Override
    public void addKindOfCourse(KindOfCourse kindOfCourse) {
        getSession().save(kindOfCourse);
    }

    @Override
    public void deleteKindOfCourse(Integer id) {
        KindOfCourse kindOfCourse = (KindOfCourse) getSession().get(KindOfCourse.class, id);
        getSession().delete(kindOfCourse);
    }

    @Override
    public void updateKindOfCourse(KindOfCourse kindOfCourse) {
        getSession().update(kindOfCourse);
    }

    @Override
    public void initializeCountOfCourses(KindOfCourse kindOfCourse) {
        Query q = getSession().createQuery("SELECT count(id) FROM Course WHERE kindOfCourse="+kindOfCourse.getTypeId().toString());
        kindOfCourse.setCountOfCourses((Long) q.uniqueResult());
    }

    @Override
    public List<KindOfCourse> getAllKindOfCourse() {
        return getSession().createCriteria(KindOfCourse.class).list();
    }
}